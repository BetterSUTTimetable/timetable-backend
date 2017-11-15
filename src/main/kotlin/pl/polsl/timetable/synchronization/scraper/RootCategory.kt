package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.Course

class RootCategory(
        private val document: Document,
        private val innerCategoryFactory: (Long) -> Category
): Category {
    override val name: String = "root"

    override val subcategories: List<Category> by lazy {
        val tree = document.select(".main_tree").first()
        val elements = tree.select("span")

        elements.map {
            val id = it.parent().id().toLong()
            innerCategoryFactory(id)
        }.toList()
    }

    override val courses: List<Course> = emptyList()
}