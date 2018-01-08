package pl.polsl.timetable.course.filter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.user.JpaUser
import pl.polsl.timetable.user.User
import pl.polsl.timetable.user.UserRepository
import pl.polsl.timetable.util.InvalidIdException
import java.time.LocalTime
import java.time.ZoneId

@Service
@Transactional
class DefaultCourseFilterService(
    private val courseFilterRepository: CourseFilterRepository,
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
): CourseFilterService {
    @Throws(InvalidIdException::class)
    override fun createFilter(user: User, filterDefinition: CourseFilterDefinition) {
        val jpaUser = userRepository
                .findOne(user.id)
                ?: throw InvalidIdException("User ${user.id} doesn't exists!")

        val course = courseRepository
                .findOne(filterDefinition.courseId)
                ?: throw InvalidIdException("Course ${filterDefinition.courseId} doesn't exists!")

        val week = if (filterDefinition.week == WeekInterval.EveryWeek) {
            Week.EveryWeek
        } else if (Week.EvenWeek(course.beginTime)) {
            Week.EvenWeek
        } else {
            Week.OddWeek
        }

        val filter = JpaCourseFilterData.create(
                category = course.category,
                courseType = course.courseType,
                fullCourseName = course.name.fullName,
                week = week,
                time = LocalTime.from(course.beginTime.atZone(ZoneId.of("GMT"))),
                duration = course.duration
        )

        jpaUser.jpaFilters.add(filter)
        courseFilterRepository.save(filter)
        userRepository.save(jpaUser)
    }

    @Throws(InvalidIdException::class)
    override fun deleteFilter(user: User, filterId: Long) {
        val filter = courseFilterRepository.findOne(filterId)
        val jpaUser =  userRepository.findOne(user.id)
        if (jpaUser.jpaFilters.removeIf { it.id == filterId }) {
            userRepository.save(jpaUser)
            courseFilterRepository.delete(filter)
        } else {
            throw InvalidIdException("Filter $filterId is not in user's ${user.id} filters!")
        }
    }

    override fun filters(user: User): List<IdentifiableCourseFilterData> {
        val jpaUser = userRepository.findOne(user.id)
        return jpaUser.filters.flatMap { it.value }
    }
}