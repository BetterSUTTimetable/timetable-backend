package pl.polsl.timetable.synchronization.scraper

import org.jsoup.nodes.Document
import pl.polsl.timetable.course.Course

class EmptyCategory(document: Document) : Category {
    override val name = findGroupName(document)

    override fun courses(): List<Course> = emptyList()

    override val subcategories: List<Category> = emptyList()
}