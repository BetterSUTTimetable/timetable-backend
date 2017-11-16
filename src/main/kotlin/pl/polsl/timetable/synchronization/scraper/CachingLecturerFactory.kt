package pl.polsl.timetable.synchronization.scraper

import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import pl.polsl.timetable.course.lecturer.Lecturer
import java.net.URL

@Component
class CachingLecturerFactory: (String, URL) -> Lecturer {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(value = "lecturers")
    override fun invoke(shortName: String, url: URL): Lecturer {
        logger.trace("Parsing lecturer $shortName: $url")
        return ParsedLecturer(Jsoup.connect(url.toString() + "&winW=1584&winH=818&loadBG=000000").get(), shortName)
    }

}