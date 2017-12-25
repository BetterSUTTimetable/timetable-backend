package pl.polsl.timetable.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

class JpaUserTests {

    @Test
    fun `serialization with jackson should succeed`() {
        val user: User = JpaUser(
                email = "email@email.com",
                passwordHash = "password_hash"
        )

        val mapper = ObjectMapper()

        mapper.writeValueAsString(user)
    }
}