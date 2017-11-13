package pl.polsl.timetable.course.name

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseNameRepository : JpaRepository<JpaCourseName, Long> {
    fun findByFullNameAndShortName(fullName: String, shortName: String): Optional<JpaCourseName>
}