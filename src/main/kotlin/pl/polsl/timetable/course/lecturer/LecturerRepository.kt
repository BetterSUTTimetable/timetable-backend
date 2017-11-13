package pl.polsl.timetable.course.lecturer

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LecturerRepository: JpaRepository<JpaLecturer, Long> {
    fun findByFullNameAndShortName(fullName: String, shortName: String): Optional<JpaLecturer>
}