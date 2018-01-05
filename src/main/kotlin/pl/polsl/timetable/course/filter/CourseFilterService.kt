package pl.polsl.timetable.course.filter

import pl.polsl.timetable.user.User
import pl.polsl.timetable.util.InvalidIdException

interface CourseFilterService {
    @Throws(InvalidIdException::class)
    fun createFilter(user: User, filterDefinition: CourseFilterDefinition)

    @Throws(InvalidIdException::class)
    fun deleteFilter(user: User, filterId: Long)

    fun filters(user: User): List<IdentifiableCourseFilterData>
}