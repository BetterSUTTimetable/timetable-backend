package pl.polsl.timetable.course.category

import com.fasterxml.jackson.annotation.JsonIgnore

class ChildrenAwareCategory(
        category: IdentifiableCategory,
        val subcategories: Set<IdentifiableCategory>,
        val hasCourses: Boolean
): IdentifiableCategory {
    init {
        if (subcategories.any { it.parent != category }) {
            throw RuntimeException("Invalid subcategories parent!")
        }
    }

    override val id: Long = category.id

    @JsonIgnore
    override val parent: IdentifiableCategory? = category.parent

    override val name: String = category.name
}