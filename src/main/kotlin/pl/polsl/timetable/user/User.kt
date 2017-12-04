package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.filter.CourseFilterData

interface User {
    val email: String
    val passwordHash: String
    val selectedCategories: Set<IdentifiableCategory>
    val favoriteCategories: Set<IdentifiableCategory>
    val filters: Map<IdentifiableCategory, List<CourseFilterData>>
}