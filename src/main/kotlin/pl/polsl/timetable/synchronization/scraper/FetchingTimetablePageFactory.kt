package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.polsl.timetable.course.lecturer.Lecturer
import java.net.URL

@Component
class FetchingTimetablePageFactory(
    @Autowired
    private val lecturerFactory: (String, URL) -> Lecturer
): TimetablePageFactory {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun create(url: URL): TimetablePage {
        return ParsedTimetablePage(
                Jsoup.connect(url.toString()).get(),
                IcsFileDownloader(),
                lecturerFactory
        )
    }

    fun createLecturer(shortName: String, url: URL): Lecturer {
        logger.trace("Parsing lecturer $shortName: $url")
        return ParsedLecturer(Jsoup.connect(url.toString()+"&winW=1584&winH=818&loadBG=000000").get(), shortName)
    }

}

