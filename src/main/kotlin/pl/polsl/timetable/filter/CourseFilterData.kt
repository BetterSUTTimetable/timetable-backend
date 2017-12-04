package pl.polsl.timetable.filter

import pl.polsl.timetable.course.CourseType
import pl.polsl.timetable.course.category.IdentifiableCategory
import java.time.Duration
import java.time.LocalTime

interface CourseFilterData {
    val courseType: CourseType
    val fullCourseName: String
    val week: Week
    val time: LocalTime
    val duration: Duration
}