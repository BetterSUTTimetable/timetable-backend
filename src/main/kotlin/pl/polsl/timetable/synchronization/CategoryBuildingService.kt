package pl.polsl.timetable.synchronization

import pl.polsl.timetable.synchronization.scraper.Category

interface CategoryBuildingService {
    fun recreate(rootCategory: Category)
}