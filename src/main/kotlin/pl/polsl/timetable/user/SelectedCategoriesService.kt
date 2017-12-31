package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.IdentifiableCategory

interface SelectedCategoriesService {
    fun addSelectedCategory(user: User, categoryId: Long)
    fun removeSelectedCategory(user: User, categoryId: Long)
    fun selectedCategories(user: User): Set<IdentifiableCategory>
}