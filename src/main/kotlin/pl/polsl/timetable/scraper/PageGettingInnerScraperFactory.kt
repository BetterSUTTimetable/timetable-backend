package pl.polsl.timetable.scraper

import org.jsoup.Jsoup

class PageGettingInnerScraperFactory: InnerScraperFactory {
    override fun create(id: Long): CategoryScraper {
        val document = Jsoup.connect("https://plan.polsl.pl/left_menu_feed.php?type=1&branch=$id&link=0").get()
        return InnerCategoryScraper(document, this)
    }
}