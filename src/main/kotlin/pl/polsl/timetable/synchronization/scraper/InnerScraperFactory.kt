package pl.polsl.timetable.synchronization.scraper

interface InnerScraperFactory {
    fun create(id: Long): CategoryScraper
}