package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.IdentifiableCategory

interface User {
    val email: String
    val passwordHash: String
    val selectedCategories: Set<IdentifiableCategory>
    val favoriteCategories: Set<IdentifiableCategory>
}