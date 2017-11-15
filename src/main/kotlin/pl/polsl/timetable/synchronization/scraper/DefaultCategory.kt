package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Course

data class DefaultCategory(
        override val name: String,
        override val subcategories: List<Category>,
        override val courses: List<Course> = emptyList()
) : Category