package pl.polsl.timetable.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class InnerCategoryScraper(
        private val document: Document,
        private val innerScraperFactory: InnerScraperFactory
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
                .map { (li, divs) ->
                    val id = divs.first().id().substring(4).toLong()
                    innerScraperFactory.create(id).scrape()
                }
                .toList()

        val timetables = listElements
                .filter {
                    it.second.isEmpty()
                }
                .map { it.first }
                .map { it.select("a").attr("href") }
                .map(::DefaultTimetablePage)
                .toList()


        return DefaultCategory(name, categories, timetables)
    }
}