package pl.polsl.timetable.scraper

import org.junit.Assert
import org.junit.Test

class ScraperTest {
    @Test
    fun noExceptionTest() {
        val category = MainCategoryScraper().scrape()
        Assert.assertTrue(category.subcategories.size > 0)
    }
}