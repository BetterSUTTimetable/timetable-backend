package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Course
import pl.polsl.timetable.synchronization.CoursesBuilder

class LeafCategory(
        private val timetablePage: TimetablePage,
        private val coursesBuilder: CoursesBuilder
): Category {
    override val name: String = timetablePage.groupName

    override val subcategories: List<Category> = emptyList()

    override fun courses(): List<Course> = coursesBuilder.build(timetablePage)
}