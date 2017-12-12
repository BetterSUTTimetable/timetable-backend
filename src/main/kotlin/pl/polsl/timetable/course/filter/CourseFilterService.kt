package pl.polsl.timetable.course.filter

interface CourseFilterService {
    fun createFilterForUser(userId: Long, filterDefinition: CourseFilterDefinition)
}