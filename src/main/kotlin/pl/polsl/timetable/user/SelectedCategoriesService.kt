package pl.polsl.timetable.user

interface SelectedCategoriesService {
    fun addSelectedCategory(user: User, categoryId: Long)
    fun removeSelectedCategory(user: User, categoryId: Long)
}