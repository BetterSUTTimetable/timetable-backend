package pl.polsl.timetable.course.room

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClassroomRepository: JpaRepository<JpaClassroom, Long> {
    fun findByRoom(room: String): Optional<JpaClassroom>
}