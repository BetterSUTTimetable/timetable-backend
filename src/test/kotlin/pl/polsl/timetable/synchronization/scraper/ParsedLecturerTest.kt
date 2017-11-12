package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import java.io.File

class ParsedLecturerTest {
    private val page = File(ParsedLecturerTest::class.java.getResource("lecturer.html").toURI())
    private val document = Jsoup.parse(page, "iso-8859-2")
    private val lecturer = ParsedLecturer(document, "JaFrą")

    @Test
    fun correctNamesTest() {
        Assert.assertEquals("JaFrą", lecturer.shortName)
        Assert.assertEquals("dr inż. Jacek Frączek", lecturer.fullName)
    }
}