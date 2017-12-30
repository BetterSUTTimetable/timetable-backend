package pl.polsl.timetable.user

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.category.IdentifiableCategory
import java.security.Principal

@Service
@Transactional
class DefaultUserService(
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository,
        private val passwordEncoder: PasswordEncoder
): UserService {
    override fun create(userData: UserLoginData) {
        val validator = EmailValidator()

        if (!validator.isValid(userData.email, null)) {
            throw InvalidUsernameException("'${userData.email}' is not a valid email!")
        }

        if (userRepository.findByEmail(userData.email).isPresent) {
            throw UsernameCollisionException("User already exists!")
        } else {
            val user = JpaUser(userData.email, passwordEncoder.encode(userData.password))
            userRepository.save(user)
        }
    }

    override fun find(principal: Principal): JpaUser {
        return userRepository
                .findByEmail(principal.name)
                .orElseThrow { UsernameNotFoundException("There is no user ${principal.name}!") }
    }

    override fun addSelectedCategory(principal: Principal, categoryId: Long) {
        val category = categoryRepository.getOne(categoryId)
        val user = find(principal)
        user.selectedCategories.add(category)
        userRepository.save(user)
    }

    override fun removeSelectedCategory(principal: Principal, categoryId: Long) {
        val user = find(principal)
        user.selectedCategories.removeIf { it.id == categoryId }
        userRepository.save(user)
    }

    override fun seletedCategory(principal: Principal, categoryId: Long): IdentifiableCategory {
        val user = find(principal)
        return user.selectedCategories.first { it.id == categoryId }
    }
}

