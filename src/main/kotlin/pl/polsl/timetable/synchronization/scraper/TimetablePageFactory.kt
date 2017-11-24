package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result
import java.net.URL

interface TimetablePageFactory {
    fun create(url: URL): Result<TimetablePage, Throwable>
}