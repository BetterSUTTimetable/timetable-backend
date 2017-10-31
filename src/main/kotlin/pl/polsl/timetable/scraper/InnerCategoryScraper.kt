package pl.polsl.timetable.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class InnerCategoryScraper(val name: String, id: Long): CategoryScraper {
    val document = Jsoup.connect("https://plan.polsl.pl/left_menu_feed.php?type=1&branch=$id&link=0").get()

    override fun scrape(): Category {
        val listElements = document.select("li").map {
            it to it.select("div")
        }

        val categories = listElements
                .filter {
                    it.second.isNotEmpty()
                }
                .map { (li, divs) ->
                    val id = divs.first().id().substring(4).toLong()
                    val name = li.text()
                    InnerCategoryScraper(name, id).scrape()
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