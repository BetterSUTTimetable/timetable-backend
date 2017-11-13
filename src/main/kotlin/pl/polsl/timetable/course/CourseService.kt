package pl.polsl.timetable.course

import java.time.Instant

interface CourseService {
    fun coursesBetween(categoryId: Long, from: Instant, to: Instant): List<Course>
}