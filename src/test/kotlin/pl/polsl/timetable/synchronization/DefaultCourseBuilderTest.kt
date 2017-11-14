package pl.polsl.timetable.synchronization

import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import pl.polsl.timetable.course.lecturer.DefaultLecturer
import pl.polsl.timetable.synchronization.parser.DefaultIcsFileParser
import pl.polsl.timetable.synchronization.parser.IcsFileDownloaderTest
import pl.polsl.timetable.synchronization.scraper.IcsFile
import pl.polsl.timetable.synchronization.scraper.ParsedTimetablePage
import pl.polsl.timetable.synchronization.scraper.TimetablePage
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.URL

class DefaultCourseBuilderTest {
    private val page = File(DefaultCourseBuilderTest::class.java.getResource("scraper/timetable_page.html").toURI())
    private val document = Jsoup.parse(page, "iso-8859-2")
    private val timetable: TimetablePage = ParsedTimetablePage(document, this::createIcsFile, this::createLecturer)

    init{
        document.setBaseUri("https://plan.polsl.pl")
    }

    @Test
    fun basicTest() {
        val builder = DefaultCourseBuilder(DefaultIcsFileParser())
        val courses = builder.build(timetable)
        Assert.assertTrue(courses.isNotEmpty())
    }

    private fun createIcsFile(url: String): BufferedReader {
        val file = File(IcsFileDownloaderTest::class.java.getResource("test.ics").toURI())
        return BufferedReader(FileReader(file))
    }

    private fun createLecturer(name: String, url: URL) = DefaultLecturer(name, name)
}