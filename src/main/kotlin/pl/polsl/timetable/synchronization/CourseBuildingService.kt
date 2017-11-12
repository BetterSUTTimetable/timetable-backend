package pl.polsl.timetable.synchronization

import pl.polsl.timetable.synchronization.scraper.TimetablePage

interface CourseBuildingService {
    fun insertPageData(page: TimetablePage)
}