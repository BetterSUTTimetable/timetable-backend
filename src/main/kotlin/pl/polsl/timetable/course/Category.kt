package pl.polsl.timetable.course

interface Category {
    val subcategories: List<Category>
    val classes: List<Course>
}