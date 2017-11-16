package pl.polsl.timetable.course.category

interface CategoryService {
    fun rootCategories(): Set<IdentifiableCategory>
    fun subcategoriesOf(id: Long): Set<IdentifiableCategory>
    fun category(id: Long): ChildrenAwareCategory
}