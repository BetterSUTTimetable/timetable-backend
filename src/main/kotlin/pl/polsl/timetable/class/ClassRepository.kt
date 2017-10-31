package pl.polsl.timetable.`class`

import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp

interface ClassRepository: JpaRepository<JpaClass, Long> {
    fun findByBeginTimestampBetweenOrderByBeginTimestampDesc(begin: Timestamp, end: Timestamp): List<JpaClass>
}