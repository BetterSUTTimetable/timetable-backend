package pl.polsl.timetable.course

import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp

interface CourseRepository : JpaRepository<JpaCourse, Long> {
    fun findByBeginTimestampBetweenOrderByBeginTimestampDesc(begin: Timestamp, end: Timestamp): List<JpaCourse>
}