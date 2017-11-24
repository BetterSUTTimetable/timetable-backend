package pl.polsl.timetable.synchronization.scraper

import com.github.michaelbull.result.Result

interface CategoryScraper {
    fun scrape(): Result<Category, Throwable>
}