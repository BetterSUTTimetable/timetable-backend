package pl.polsl.timetable.synchronization

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
    override fun recreate(rootCategory: Category) {
        rootCategory.subcategories.forEach { recreate(it, null) }
    }

    private fun recreate(category: Category, parent: JpaCategory?) {
        val jpaCategory = categoryCreationService.findOrCreate(parent, category.name)

        courseRepository.deleteByCategory(jpaCategory)
        courseBuildingService.updateCourses(jpaCategory, category.courses)

        category.subcategories.forEach { recreate(it, jpaCategory) }
    }
}