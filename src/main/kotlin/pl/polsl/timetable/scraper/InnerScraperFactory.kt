package pl.polsl.timetable.scraper

interface InnerScraperFactory {
    fun create(id: Long): CategoryScraper
}