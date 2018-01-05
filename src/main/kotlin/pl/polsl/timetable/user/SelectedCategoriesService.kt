package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.CategoryNotFoundException
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.util.InvalidIdException

interface SelectedCategoriesService {
    @Throws(NoCoursesInCategoryException::class)
    fun addSelectedCategory(user: User, categoryId: Long)

    @Throws(InvalidIdException::class)
    fun removeSelectedCategory(user: User, categoryId: Long)

    fun selectedCategories(user: User): Set<IdentifiableCategory>
}