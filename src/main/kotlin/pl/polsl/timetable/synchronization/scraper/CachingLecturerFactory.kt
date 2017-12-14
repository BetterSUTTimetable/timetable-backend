package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import pl.polsl.timetable.course.lecturer.Lecturer
import java.net.URL

@Component
class CachingLecturerFactory: (String, URL) -> Result<Lecturer, Throwable> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(value = "lecturers")
    override fun invoke(shortName: String, url: URL): Result<Lecturer, Throwable> {
        logger.trace("Parsing lecturer $shortName: $url")
        return Result.of {
            ParsedLecturer(Jsoup.connect(url.toString() + "&winW=1584&winH=818&loadBG=000000").get(), shortName)
        }
    }

}