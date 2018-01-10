package pl.polsl.timetable.synchronization

import com.github.michaelbull.result.Result
import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import pl.polsl.timetable.course.lecturer.DefaultLecturer
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.room.DefaultClassroom
import pl.polsl.timetable.synchronization.parser.DefaultIcsFileParser
import pl.polsl.timetable.synchronization.parser.IcsFileDownloaderTest
import pl.polsl.timetable.synchronization.scraper.ParsedTimetablePage
import pl.polsl.timetable.synchronization.scraper.TimetablePage
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.StringReader
import java.net.URL

class DefaultCourseBuilderTest {
    private val page = File(DefaultCourseBuilderTest::class.java.getResource("scraper/timetable_page.html").toURI())
    private val document = Jsoup.parse(page, "iso-8859-2")
    private val timetable: TimetablePage

    init{
        document.setBaseUri("https://plan.polsl.pl")
        timetable = ParsedTimetablePage(document, this::createIcsFile, this::createLecturer)
    }

    private fun createIcsFile(url: String): BufferedReader {
        val file = File(IcsFileDownloaderTest::class.java.getResource("test.ics").toURI())
        return BufferedReader(FileReader(file))
    }

    private fun createLecturer(name: String, url: URL) = Result.of { DefaultLecturer(name, name) }

    @Test
    fun basicTest() {
        val builder = DefaultCourseBuilder(DefaultIcsFileParser())
        val courses = builder.build(timetable)
        Assert.assertTrue(courses.isNotEmpty())
    }

    @Test
    fun wfTest() {
        val entry = """BEGIN:VCALENDAR
BEGIN:VEVENT
DTSTART:20171002T090000Z
DTEND:20171002T103000Z
DTSTAMP:20180110T232318Z
UID:20171002090000
CLASS:PUBLIC
SEQUENCE:0
STATUS:CONFIRMED
SUMMARY:WF   WF OSiR
TRANSP:OPAQUE
END:VEVENT
END:VCALENDAR
"""
        val builder = DefaultCourseBuilder(DefaultIcsFileParser())
        val timetable = Mockito.mock(TimetablePage::class.java)
        val expectedLecturer = DefaultLecturer("Prow. WF", "WF")
        val expectedClassroom = DefaultClassroom("OSiR")

        Mockito.`when`(timetable.groupName).thenReturn("Grupa")
        Mockito.`when`(timetable.classrooms).thenReturn(setOf(expectedClassroom))
        Mockito.`when`(timetable.lecturers).thenReturn(setOf(expectedLecturer))
        Mockito.`when`(timetable.icsFile).thenReturn(BufferedReader(StringReader(entry)))

        val course = builder.build(timetable).first()

        Assert.assertEquals("WF", course.name.shortName)
        Assert.assertEquals("WF", course.name.fullName)

        val lecturer = course.lecturers.first()
        Assert.assertEquals(lecturer, expectedLecturer)

        val classroom = course.classrooms.first()
        Assert.assertEquals(classroom, expectedClassroom)
    }
}