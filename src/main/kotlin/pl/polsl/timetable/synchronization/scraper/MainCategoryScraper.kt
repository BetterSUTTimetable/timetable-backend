package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document

class MainCategoryScraper(
        private val document: Document,
        private val innerScraperFactory: InnerScraperFactory
) : CategoryScraper {
    override fun scrape(): Category {
        val tree = document.select(".main_tree").first()
        val elements = tree.select("span")
        val categories = elements.map {
            val id = it.parent().id().toLong()
            innerScraperFactory.create(id).scrape()
        }.toList()
        return DefaultCategory("Grupy", categories, emptyList())
    }
}