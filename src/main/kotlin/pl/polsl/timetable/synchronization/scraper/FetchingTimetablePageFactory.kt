package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.polsl.timetable.course.lecturer.Lecturer
import java.net.URL

@Component
class FetchingTimetablePageFactory(
    @Autowired
    private val lecturerFactory: (String, URL) -> Result<Lecturer, Throwable>
): TimetablePageFactory {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun create(url: URL): Result<TimetablePage, Throwable> {
        val timetable = Result.of {
            ParsedTimetablePage(
                    Jsoup.connect(url.toString()).get(),
                    IcsFileDownloader(),
                    lecturerFactory
            )
        }

        timetable.onFailure { logger.warn("Cannot create timetable from $url . $it") }

        return timetable
    }
}

