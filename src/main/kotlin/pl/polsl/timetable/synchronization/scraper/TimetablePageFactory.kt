package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import org.jsoup.nodes.Document
import java.net.URL

interface TimetablePageFactory {
    fun create(document: Document): Result<TimetablePage, Throwable>
}