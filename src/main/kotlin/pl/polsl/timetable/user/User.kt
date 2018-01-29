package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.course.filter.IdentifiableCourseFilterData

interface User {
    val id: Long
    val email: String
    val passwordHash: String
    val selectedCategories: Set<IdentifiableCategory>
    val filters: Map<IdentifiableCategory, List<IdentifiableCourseFilterData>>
}