package pl.polsl.timetable.scraper

class DefaultCategory(
        override val name: String,
        override val subcategories: List<Category>,
        override val timetables: List<TimetablePage>
) : Category