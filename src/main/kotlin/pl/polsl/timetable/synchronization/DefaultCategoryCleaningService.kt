package pl.polsl.timetable.synchronization

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.CategoryService
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.course.filter.CourseFilterRepository
import pl.polsl.timetable.synchronization.scraper.Category
import pl.polsl.timetable.user.SelectedCategoriesService
import pl.polsl.timetable.user.UserRepository

@Service
@Transactional
class DefaultCategoryCleaningService(
        private val categoryService: CategoryService,
        private val categoryRepository: CategoryRepository,
        private val userRepository: UserRepository,
        private val courseRepository: CourseRepository,
        private val selectedCategoriesService: SelectedCategoriesService
): CategoryCleaningService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun removeNonexistentCategories(rootCategory: Category) {
        val jpaCategories = categoryRepository.findByParent(null).toList()
        val rootCategories = rootCategory.subcategories

        recursiveClean(rootCategories, jpaCategories)
    }

    private fun recursiveClean(pageCategories: List<Category>, databaseCategories: List<JpaCategory>) {
        clean(pageCategories, databaseCategories)

        for (category in pageCategories) {
            val jpaCategory = databaseCategories.first { it.name == category.name }
            val jpaSubcategories = categoryRepository
                    .findByParent(jpaCategory)
                    .toList()

            recursiveClean(category.subcategories, jpaSubcategories)
        }
    }

    private fun clean(pageCategories: List<Category>, databaseCategories: List<JpaCategory>) {
        databaseCategories
                .filter { jpaCategory -> pageCategories.none {  it.name == jpaCategory.name } }
                .forEach {
                    remove(it)
                }
    }

    private fun remove(jpaCategory: JpaCategory) {
        logger.trace("Removing ${jpaCategory.name} (${jpaCategory.id}) category!")

        try {
            val subcategories = categoryRepository.findByParent(categoryRepository.findOne(jpaCategory.id))

            if (subcategories.isEmpty()) {
                courseRepository.deleteByCategory(categoryRepository.findOne(jpaCategory.id))
                userRepository
                        .findAll()
                        .forEach {
                            try {
                                if (selectedCategoriesService.selectedCategories(it).any { it.id == jpaCategory.id }) {
                                    selectedCategoriesService.removeSelectedCategory(it, jpaCategory.id)
                                }
                            } catch (e: Exception) {
                                logger.error("Failed to remove category $jpaCategory from user $it!", e)
                            }
                        }
            } else {
                subcategories.forEach { remove(it) }
            }

            categoryRepository.delete(jpaCategory.id)
        } catch (e: Exception) {
            logger.error("Failed to remove category $jpaCategory!", e)
        }
    }
}