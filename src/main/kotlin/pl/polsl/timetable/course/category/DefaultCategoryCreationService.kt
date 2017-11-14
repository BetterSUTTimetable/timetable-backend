package pl.polsl.timetable.course.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultCategoryCreationService(
        @Autowired
        private val categoryRepository: CategoryRepository
): CategoryCreationService {
    override fun findOrCreate(parent: JpaCategory?, name: String): JpaCategory {
        val categories = categoryRepository.findByNameAndParent(name, parent)

        return if (categories.isEmpty()) {
            categoryRepository.save(JpaCategory(name, parent))
        } else {
            categories.first()
        }
    }

    override fun findOrCreate(name: String): JpaCategory {
        val categories = categoryRepository.findByNameAndParent(name, null)

        return if (categories.isEmpty()) {
            categoryRepository.save(JpaCategory(name, null))
        } else {
            categories.first()
        }
    }
}