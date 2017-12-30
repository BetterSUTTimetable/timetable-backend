package pl.polsl.timetable.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.category.CategoryRepository

@Service
@Transactional
class DefaultSelectedCategoriesService(
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
): SelectedCategoriesService {

    override fun addSelectedCategory(user: User, categoryId: Long) {
        val category = categoryRepository.findOne(categoryId)
        user as JpaUser
        user.selectedCategories.add(category)
        userRepository.save(user)
    }

    override fun removeSelectedCategory(user: User, categoryId: Long) {
        user as JpaUser
        user.selectedCategories.removeIf { categoryId == it.id }
        userRepository.save(user)
    }
}