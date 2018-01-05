package pl.polsl.timetable.course.category

interface CategoryService {
    fun rootCategories(): Set<IdentifiableCategory>

    @Throws(CategoryNotFoundException::class)
    fun subcategoriesOf(id: Long): Set<IdentifiableCategory>

    @Throws(CategoryNotFoundException::class)
    fun category(id: Long): ChildrenAwareCategory
}