package pl.polsl.timetable.course.filter

import org.springframework.stereotype.Service
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.user.JpaUser
import pl.polsl.timetable.user.UserRepository
import java.time.LocalTime
import java.time.ZoneId

@Service
class DefaultCourseFilterService(
    private val courseFilterRepository: CourseFilterRepository,
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
): CourseFilterService {
    override fun createFilterForUser(userId: Long, filterDefinition: CourseFilterDefinition) {
        val user = userRepository.findOne(userId)
        val course = courseRepository.findOne(filterDefinition.courseId)

        val filter = JpaCourseFilterData.create(
                category = course.category,
                courseType = course.courseType,
                fullCourseName = course.name.fullName,
                week = filterDefinition.week,
                time = LocalTime.from(course.beginTime.atZone(ZoneId.of("GMT"))),
                duration = course.duration
        )

        user.jpaFilters.add(filter)
        courseFilterRepository.save(filter)
        userRepository.save(user)
    }
}