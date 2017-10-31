package pl.polsl.timetable.scraper

import org.jsoup.Jsoup

class MainCategoryScraper : CategoryScraper {
    val document = Jsoup.connect("https://plan.polsl.pl/left_menu.php").get()

    override fun scrape(): Category {
        val tree = document.select(".main_tree").first()
        val elements = tree.select("span")
        val categories = elements.map {
            val id = it.parent().id().toLong()
            val name = it.text()
            InnerCategoryScraper(name, id).scrape()
        }.toList()
        return DefaultCategory("Grupy", categories, emptyList())
    }
}