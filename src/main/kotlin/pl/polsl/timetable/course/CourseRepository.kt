package pl.polsl.timetable.course

import org.springframework.data.jpa.repository.JpaRepository
import pl.polsl.timetable.course.category.JpaCategory
import java.sql.Timestamp

interface CourseRepository : JpaRepository<JpaCourse, Long> {
    fun findByBeginTimestampBetweenOrderByBeginTimestampAsc(begin: Timestamp, end: Timestamp): List<JpaCourse>
    fun findByBeginTimestampBetweenAndCategoryOrderByBeginTimestampAsc(begin: Timestamp, end: Timestamp, category: JpaCategory): List<JpaCourse>
    fun findByCategoryOrderByBeginTimestampDesc(category: JpaCategory): List<JpaCourse>
    fun deleteByCategory(category: JpaCategory): Long
    fun existsByCategory(category: JpaCategory): Boolean
}