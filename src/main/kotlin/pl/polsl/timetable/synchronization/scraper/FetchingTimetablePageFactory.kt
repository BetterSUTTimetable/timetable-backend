package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import java.net.URL

class FetchingTimetablePageFactory: TimetablePageFactory {
    override fun create(url: String): TimetablePage {
        return ParsedTimetablePage(
                Jsoup.connect("http://plan.polsl.pl/$url").get(),
                IcsFileDownloader(),
                this::createLecturer
        )
    }

    private fun createLecturer(shortName: String, url: URL)
            = ParsedLecturer(Jsoup.connect(url.toString()).get(), shortName)
}