package pl.polsl.timetable.course.filter

import pl.polsl.timetable.course.CourseType
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime

interface CourseFilterData {
    val courseType: CourseType
    val fullCourseName: String
    val week: Week
    val dayOfWeek: DayOfWeek
    val time: LocalTime
    val duration: Duration
}