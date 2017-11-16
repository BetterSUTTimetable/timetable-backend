package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Course

data class DefaultCategory(
        override val name: String,
        override val subcategories: List<Category>,
        private val courses: List<Course> = emptyList()
) : Category {
    override fun courses(): List<Course> = courses
}