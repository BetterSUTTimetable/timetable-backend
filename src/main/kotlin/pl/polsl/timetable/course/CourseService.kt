package pl.polsl.timetable.course

import java.time.Instant

interface CourseService {
    fun coursesBetween(categoryId: Long, timeRange: ClosedRange<Instant>): List<Course>
}