package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document

class InnerCategoryScraper(
        private val document: Document,
        private val innerScraperFactory: InnerScraperFactory,
        private val timetablePageFactory: TimetablePageFactory
): CategoryScraper {

    override fun scrape(): Category {
        val name = document.select("span").firstOrNull()?.text() ?: "" //TODO: error handling?
        val listElements = document.select("li").map {
            it to it.select("div")
        }

        val categories = listElements
                .filter {
                    it.second.isNotEmpty()
                }
                .map { it.second }
                .map { it ->
                    val id = it.first().id().substring(4).toLong()
                    innerScraperFactory.create(id).scrape()
                }
                .toList()

        val timetables = listElements
                .filter {
                    it.second.isEmpty()
                }
                .map { it.first }
                .map { it.select("a").attr("href") }
                .map(timetablePageFactory::create)
                .toList()

        return DefaultCategory(name, categories, timetables)
    }
}

