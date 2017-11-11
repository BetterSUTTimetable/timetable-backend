package pl.polsl.timetable.user

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class DefaultUserService(
        @Autowired
        private val userRepository: UserRepository,

        @Autowired
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

    override fun find(principal: Principal): User {
        return userRepository
                .findByEmail(principal.name ?: "")
                .orElseThrow { UsernameNotFoundException("There is no user ${principal.name}!") }
    }
}

