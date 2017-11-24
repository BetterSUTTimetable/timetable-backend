package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import pl.polsl.timetable.course.lecturer.DefaultLecturer
import java.io.BufferedReader
import java.io.File
import java.net.URL

class ParsedTimetablePageTest {
    private val page = File(ParsedTimetablePageTest::class.java.getResource("timetable_page.html").toURI())
    private val document = Jsoup.parse(page, "iso-8859-2")
    private val timetable: TimetablePage = ParsedTimetablePage(document, this::createIcsFile, this::createLecturer)

    init{
        document.setBaseUri("https://plan.polsl.pl")
    }

    @Test
    fun groupNameTest() {
        Assert.assertEquals("OS Inf sem2", timetable.groupName)
    }

    @Test
    fun sampleClassNamesTest() {
        val expectedNames = mapOf(
                "*PPP" to "Praktyka Programowania - Python",
                "MC (sek.5,6)" to "Modelowanie Cyfrowe"
        )

        for ((key, value) in expectedNames) {
            Assert.assertEquals(
                    "$key: $value no found in ${timetable.classNames}",
                    value,
                    timetable.classNames[key]
            )
        }
    }

    @Test
    fun sampleClassroomsTest() {
        val excpectedRooms = setOf(
                "A zielona",
                "428",
                "Wydz.Gór.SJO"
        )

        for (value in excpectedRooms) {
            Assert.assertTrue(
                    "Classroom $value: no found in ${timetable.classrooms}",
                    timetable.classrooms.any { it.room == value }
            )
        }
    }

    @Test
    fun sampleLecturersTest() {
        val excpectedLectureres = setOf(
                "MSk",
                "JaFrą",
                "PCz"
        )

        for (value in excpectedLectureres) {
            Assert.assertTrue(
                    "Lecturer $value: no found in ${timetable.lecturers}",
                    timetable.lecturers.any { it.shortName == value }
            )
        }
    }

    @Test
    fun icsTest() {
        //just to create it - assertion called indirectely
        val file = timetable.icsFile
    }

    private fun createIcsFile(url: String): BufferedReader {
        Assert.assertEquals("https://plan.polsl.pl/plan.php?type=0&id=652&cvsfile=true&wd=1", url)
        return Mockito.mock(BufferedReader::class.java)
    }

    private fun createLecturer(name: String, url: URL) = Result.of{ DefaultLecturer(name, name) }
}