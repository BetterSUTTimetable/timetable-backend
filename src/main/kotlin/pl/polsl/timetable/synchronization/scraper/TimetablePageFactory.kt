package pl.polsl.timetable.synchronization.scraper

interface TimetablePageFactory {
    fun create(url: String): TimetablePage
}