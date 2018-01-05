package pl.polsl.timetable.course.category

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.util.InvalidIdException

@Service
@Transactional
class DefaultCategoryService(
        private val categoryRepository: CategoryRepository,
        private val courseRepository: CourseRepository
): CategoryService {
    override fun rootCategories(): Set<IdentifiableCategory> {
        return categoryRepository.findByParent(null)
    }

    @Throws(CategoryNotFoundException::class)
    override fun subcategoriesOf(id: Long): Set<IdentifiableCategory> {
        val parent = categoryRepository.findOne(id) ?: throw CategoryNotFoundException(id)
        return categoryRepository.findByParent(parent)
    }

    @Throws(CategoryNotFoundException::class)
    override fun category(id: Long): ChildrenAwareCategory {
        val category = categoryRepository.findOne(id) ?: throw CategoryNotFoundException(id)
        val hasCourses = courseRepository.existsByCategory(category)
        return ChildrenAwareCategory(category, subcategoriesOf(id), hasCourses)
    }

}

