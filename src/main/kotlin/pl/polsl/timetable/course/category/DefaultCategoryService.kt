package pl.polsl.timetable.course.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.course.CourseRepository

@Service
class DefaultCategoryService(
        @Autowired
        private val categoryRepository: CategoryRepository,

        @Autowired
        private val courseRepository: CourseRepository
): CategoryService {
    override fun rootCategories(): Set<IdentifiableCategory> {
        return categoryRepository.findByParent(null)
    }

    override fun subcategoriesOf(id: Long): Set<IdentifiableCategory> {
        val parent = categoryRepository.findOne(id) ?: throw CategoryNotFoundException(id)
        return categoryRepository.findByParent(parent)
    }

    override fun category(id: Long): ChildrenAwareCategory {
        val category = categoryRepository.findOne(id) ?: throw CategoryNotFoundException(id)
        val hasCourses = courseRepository.existsByCategory(category)
        return ChildrenAwareCategory(category, subcategoriesOf(id), hasCourses)
    }

}

