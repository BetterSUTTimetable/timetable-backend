package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Course

interface Category {
    val name: String
    val subcategories: List<Category>
    fun courses(): List<Course>
}

