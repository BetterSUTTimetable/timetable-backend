package pl.polsl.timetable.course.filter

import pl.polsl.timetable.user.User

interface CourseFilterService {
    fun createFilter(user: User, filterDefinition: CourseFilterDefinition)
    fun deleteFilter(user: User, filterId: Long)
    fun filters(user: User): List<IdentifiableCourseFilterData>
}