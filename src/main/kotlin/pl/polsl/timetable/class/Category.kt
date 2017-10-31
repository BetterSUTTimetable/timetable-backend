package pl.polsl.timetable.`class`

interface Category {
    val subcategories: List<Category>
    val classes: List<Class>
}