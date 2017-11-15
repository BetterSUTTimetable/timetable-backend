package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Course
import pl.polsl.timetable.synchronization.CoursesBuilder

class LeafCategory(
        private val timetablePage: TimetablePage,
        private val coursesBuilder: CoursesBuilder
): Category {
    override val name: String by lazy{
        timetablePage.groupName
    }

    override val subcategories: List<Category> = emptyList()

    override val courses: List<Course> by lazy {
        coursesBuilder.build(timetablePage)
    }
}