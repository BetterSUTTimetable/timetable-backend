package pl.polsl.timetable.scraper

import org.jsoup.Jsoup

class FetchingTimetablePageFactory: TimetablePageFactory {
    override fun create(url: String): TimetablePage {
        return ParsedTimetablePage(Jsoup.connect("http://plan.polsl.pl/$url").get())
    }
}