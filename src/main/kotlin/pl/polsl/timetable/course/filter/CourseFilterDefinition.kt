package pl.polsl.timetable.course.filter

import pl.polsl.timetable.GenerateNoArg

@GenerateNoArg
data class CourseFilterDefinition(
        val courseId: Long,
        val week: Week
)