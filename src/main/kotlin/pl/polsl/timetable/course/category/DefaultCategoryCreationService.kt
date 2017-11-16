package pl.polsl.timetable.course.category

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultCategoryCreationService(
        @Autowired
        private val categoryRepository: CategoryRepository
): CategoryCreationService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun findOrCreate(parent: JpaCategory?, name: String): JpaCategory {
        logger.trace("Finding or creating category $name with parent ${parent?.id}")
        val categories = categoryRepository.findByNameAndParent(name, parent)

        return if (categories.isEmpty()) {
            logger.trace("Creating category $name with parent ${parent?.id}")
            categoryRepository.save(JpaCategory(name, parent))
        } else {
            categories.first()
        }
    }

    override fun findOrCreate(name: String): JpaCategory {
        return findOrCreate(null, name)
    }
}