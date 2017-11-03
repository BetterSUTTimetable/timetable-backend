package pl.polsl.timetable.scraper

interface TimetablePageFactory {
    fun create(url: String): TimetablePage
}