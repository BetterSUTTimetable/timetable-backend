package pl.polsl.timetable.filter

import pl.polsl.timetable.course.Course
import pl.polsl.timetable.course.CourseType
import pl.polsl.timetable.course.category.IdentifiableCategory
import java.time.Duration
import java.time.Instant

interface CourseFilter: (Course) -> Boolean {
    val courseType: CourseType
    val fullCourseName: String
    val category: IdentifiableCategory
    val week: Week
    val beginTime: Instant
    val duration: Duration
}