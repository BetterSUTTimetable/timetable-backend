package pl.polsl.timetable.synchronization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.synchronization.scraper.CategoryScraper

@Service
class DefaultSynchronizationService(
        @Autowired
        private val categoryScraper: CategoryScraper,

        @Autowired
        private val categoryBuildingService: CategoryBuildingService

): SynchronizationService {
    override fun synchronize() {
        val root = categoryScraper.scrape()
        categoryBuildingService.recreate(root)
    }
}