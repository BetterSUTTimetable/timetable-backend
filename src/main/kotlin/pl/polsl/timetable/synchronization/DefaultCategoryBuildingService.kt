package pl.polsl.timetable.synchronization

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.course.category.CategoryCreationService
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.synchronization.scraper.Category

@Service
class DefaultCategoryBuildingService(
        @Autowired
        private val courseBuildingService: CourseBuildingService,

        @Autowired
        private val categoryCreationService: CategoryCreationService,

        @Autowired
        private val courseRepository: CourseRepository
): CategoryBuildingService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun recreate(rootCategory: Category) {
        rootCategory.subcategories.forEach { recreate(it, null) }
    }

    private fun recreate(category: Category, parent: JpaCategory?) {
        try {
            logger.info("Recreating category ${category.name}")
            val jpaCategory = categoryCreationService.findOrCreate(parent, category.name)

            val courses = category.courses()
            logger.info("Deleting courses category $jpaCategory")
            courseBuildingService.updateCourses(jpaCategory, courses)
            category.subcategories.forEach { recreate(it, jpaCategory) }
        } catch (e: Exception) {
            logger.error("Cannot recreate category $category!", e)
        }
    }
}