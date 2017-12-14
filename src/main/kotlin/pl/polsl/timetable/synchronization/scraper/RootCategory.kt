package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getAll
import com.github.michaelbull.result.partition
import org.jsoup.nodes.Document
import pl.polsl.timetable.course.Course

class RootCategory(
        private val document: Document,
        private val innerCategoryFactory: (Long) -> Result<Category, Throwable>
): Category {
    override val name: String = "root"

    override val subcategories: List<Category> = {
        val tree = document.select(".main_tree").first()
        val elements = tree.select("span")

        elements.map {
            val id = it.parent().id().toLong()
            innerCategoryFactory(id)
        }
                .toList()
                .getAll()
    }()

    override fun courses(): List<Course> = emptyList()
}