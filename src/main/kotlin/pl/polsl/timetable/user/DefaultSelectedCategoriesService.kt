package pl.polsl.timetable.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.IdentifiableCategory

@Service
@Transactional
class DefaultSelectedCategoriesService(
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
): SelectedCategoriesService {

    override fun addSelectedCategory(user: User, categoryId: Long) {
        val category = categoryRepository.findOne(categoryId)
        val jpaUser = userRepository.findOne(user.id)
        jpaUser.selectedCategories.add(category)
        userRepository.save(jpaUser)
    }

    override fun removeSelectedCategory(user: User, categoryId: Long) {
        val jpaUser = userRepository.findOne(user.id)
        jpaUser.selectedCategories.removeIf { categoryId == it.id }
        userRepository.save(jpaUser)
    }

    override fun selectedCategories(user: User): Set<IdentifiableCategory> {
        val jpaUser = userRepository.findOne(user.id)
        return jpaUser.selectedCategories
    }
}