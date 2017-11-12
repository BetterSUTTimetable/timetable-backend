package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup

class FetchingInnerScraperFactory : InnerScraperFactory {
    private val timetablePageFactory = FetchingTimetablePageFactory()
    override fun create(id: Long): CategoryScraper {
        val document = Jsoup.connect("https://plan.polsl.pl/left_menu_feed.php?type=1&branch=$id&link=0").get()
        return InnerCategoryScraper(document, this, timetablePageFactory)
    }
}