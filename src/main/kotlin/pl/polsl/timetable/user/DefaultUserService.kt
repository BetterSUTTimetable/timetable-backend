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
}

