package pl.polsl.timetable.course.category

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository: JpaRepository<JpaCategory, Long> {
    fun findByParent(parent: Category?): Set<JpaCategory>
    fun findByName(name: String): Set<JpaCategory>
    fun findByNameAndParent(name: String, parent: Category?): Set<JpaCategory>
}