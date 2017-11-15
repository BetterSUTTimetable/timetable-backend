package pl.polsl.timetable.synchronization.scraper

import java.net.URL

interface TimetablePageFactory {
    fun create(url: URL): TimetablePage
}