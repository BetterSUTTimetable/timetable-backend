package pl.polsl.timetable.synchronization.scraper

interface Category {
    val name: String
    val subcategories: List<Category>
    val timetables: List<TimetablePage>
}

