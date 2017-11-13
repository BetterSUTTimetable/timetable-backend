package pl.polsl.timetable.course.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultCategoryService(
        @Autowired
        private val categoryRepository: CategoryRepository
): CategoryService {
    override fun rootCategories(): Set<IdentifiableCategory> {
        return categoryRepository.findByParent(null)
    }

    override fun subcategoriesOf(id: Long): Set<IdentifiableCategory> {
        val parent = categoryRepository.findOne(id) ?: throw CategoryNotFoundException(id)
        return categoryRepository.findByParent(parent)
    }

}

