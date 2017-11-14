package pl.polsl.timetable.course.category

interface CategoryCreationService {
    fun findOrCreate(parent: JpaCategory?, name: String): JpaCategory
    fun findOrCreate(name: String): JpaCategory
}