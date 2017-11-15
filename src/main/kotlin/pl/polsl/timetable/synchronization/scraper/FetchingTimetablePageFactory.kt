package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.net.URL

@Component
class FetchingTimetablePageFactory: TimetablePageFactory {
    override fun create(url: URL): TimetablePage {
        return ParsedTimetablePage(
                Jsoup.connect(url.toString()).get(),
                IcsFileDownloader(),
                this::createLecturer
        )
    }

    private fun createLecturer(shortName: String, url: URL)
            = ParsedLecturer(Jsoup.connect(url.toString()).get(), shortName)
}