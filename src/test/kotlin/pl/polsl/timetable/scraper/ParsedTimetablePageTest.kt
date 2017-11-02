package pl.polsl.timetable.scraper

import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test
import java.io.File

class ParsedTimetablePageTest {
    val page = File(ParsedTimetablePageTest::class.java.getResource("timetable_page.html").toURI())
    val document = Jsoup.parse(page, "iso-8859-2")

    @Test
    fun parsingTest() {
        val timetable = ParsedTimetablePage(document)
        Assert.assertTrue(timetable.classNames.isNotEmpty())
    }
}