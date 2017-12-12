package pl.polsl.timetable.course

import pl.polsl.timetable.user.User
import java.time.Instant

interface CourseService {
    fun coursesBetween(categoryId: Long, timeRange: ClosedRange<Instant>): List<Course>
    fun userCoursesBetween(user: User, timeRange: ClosedRange<Instant>): List<Course>
}