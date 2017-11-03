package pl.polsl.timetable.scraper

import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class ParsedTimetablePageTest {
    private val page = File(ParsedTimetablePageTest::class.java.getResource("timetable_page.html").toURI())
    private val document = Jsoup.parse(page, "iso-8859-2")
    private val timetable: TimetablePage = ParsedTimetablePage(document)

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
            Assert.assertEquals(
                    "Classroom $value: no found in ${timetable.classrooms}",
                    value,
                    timetable.classrooms[value]?.room
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
            Assert.assertEquals(
                    "Lecturer $value: no found in ${timetable.lecturers}",
                    value,
                    timetable.lecturers[value]?.shortName
            )
        }
    }
}