package pl.polsl.timetable.course.filter

import pl.polsl.timetable.course.Course

class CourseFilterComposite(
        private val filters: Collection<(Course) -> Boolean>
): (Course) -> Boolean {
    override fun invoke(course: Course): Boolean {
        return filters.any { it(course) }
    }
}