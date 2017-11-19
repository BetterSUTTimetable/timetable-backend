package pl.polsl.timetable.synchronization

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import pl.polsl.timetable.synchronization.scraper.CategoryScraper

@Service
class DefaultSynchronizationService(
        @Autowired
        private val categoryScraper: CategoryScraper,

        @Autowired
        private val categoryBuildingService: CategoryBuildingService

): SynchronizationService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(fixedRate = 24*60*60*1000, initialDelay = 0)
    override fun synchronize() {
        logger.info("Starting synchronization procedure...")
        val root = categoryScraper.scrape()
        categoryBuildingService.recreate(root)
        logger.info("Synchronization finished!")
    }
}