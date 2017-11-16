package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.Course
import java.net.URL

class InnerCategory(
        private val document: Document,
        private val innerCategoryFactory: (Long) -> Category,
        private val leafCategoryFactory: (URL) -> Category
): Category {
    override val name = document.select("span").firstOrNull()?.text() ?: ""

    override fun courses(): List<Course> = emptyList()

    override val subcategories: List<Category> = {
        val listElements = document.select("li").map {
            it to it.select("div")
        }

        val innerCategories = listElements
                .filter {
                    it.second.isNotEmpty()
                }
                .map { it.second }
                .map { it ->
                    val id = it.first().id().substring(4).toLong()
                    innerCategoryFactory(id)
                }
                .toList()

        val leafCategories = listElements
                .filter {
                    it.second.isEmpty()
                }
                .map { it.first.select("a").firstOrNull() }
                .filterNotNull()
                .map { URL(it.absUrl("href"))}
                .map(leafCategoryFactory)
                .toList()

        innerCategories + leafCategories
    }()
}