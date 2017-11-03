package pl.polsl.timetable.scraper

import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test

class ScraperTest {
    @Test
    fun noExceptionTest() {
        val document = Jsoup.connect("https://plan.polsl.pl/left_menu.php").get()
        val category = MainCategoryScraper(document, FetchingInnerScraperFactory()).scrape()
        Assert.assertTrue(category.subcategories.size > 0)
    }
}