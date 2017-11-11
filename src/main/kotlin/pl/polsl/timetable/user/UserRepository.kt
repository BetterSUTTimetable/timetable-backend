package pl.polsl.timetable.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<JpaUser, Long> {
    fun findByEmail(email: String): Optional<JpaUser>
}