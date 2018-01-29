package pl.polsl.timetable.synchronization
import pl.polsl.timetable.synchronization.scraper.Category

interface CategoryCleaningService {
    fun removeNonexistentCategories(rootCategory: Category)
}