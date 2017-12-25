package pl.polsl.timetable.course.category

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DefaultCategoryCreationService(
        private val categoryRepository: CategoryRepository
): CategoryCreationService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun findOrCreate(parent: JpaCategory?, name: String): JpaCategory {
        val categories = categoryRepository.findByNameAndParent(name, parent)

        return if (categories.isEmpty()) {
            logger.trace("Category $name with parent ${parent?.id} not found! Creating...")
            categoryRepository.save(JpaCategory(name, parent))
        } else {
            logger.trace("Category $name with parent ${parent?.id} found!")
            categories.first()
        }
    }

    override fun findOrCreate(name: String): JpaCategory {
        return findOrCreate(null, name)
    }
}