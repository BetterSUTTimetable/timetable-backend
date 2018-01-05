package pl.polsl.timetable.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.CategoryService
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.util.InvalidIdException

@Service
@Transactional
class DefaultSelectedCategoriesService(
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository,
        private val categoryService: CategoryService
): SelectedCategoriesService {

    @Throws(NoCoursesInCategoryException::class)
    override fun addSelectedCategory(user: User, categoryId: Long) {
        if (categoryService.category(categoryId).hasCourses) {
            val category = categoryRepository.findOne(categoryId)
            val jpaUser = userRepository.findOne(user.id)
            jpaUser.selectedCategories.add(category)
            userRepository.save(jpaUser)
        } else {
           throw NoCoursesInCategoryException("Category $categoryId doesn't have any courses!")
        }
    }

    @Throws(InvalidIdException::class)
    override fun removeSelectedCategory(user: User, categoryId: Long) {
        val jpaUser = userRepository.findOne(user.id)
        if (jpaUser.selectedCategories.removeIf { categoryId == it.id }) {
            userRepository.save(jpaUser)
        } else {
            throw InvalidIdException("Category $categoryId is not in user ${user.id} selected categories!")
        }
    }

    override fun selectedCategories(user: User): Set<IdentifiableCategory> {
        val jpaUser = userRepository.findOne(user.id)
        return jpaUser.selectedCategories
    }
}

