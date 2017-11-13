package pl.polsl.timetable.course.category

interface Category {
    val name: String
    val parent: Category?
}