package pl.polsl.timetable.course.category

interface IdentifiableCategory: Category {
    val id: Long
    override val parent: IdentifiableCategory?
}