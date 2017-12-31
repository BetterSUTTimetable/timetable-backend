package pl.polsl.timetable.course

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.polsl.timetable.course.category.CategoryNotFoundException
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.filter.CourseFilterComposite
import pl.polsl.timetable.course.filter.DataBasedCourseFilter
import pl.polsl.timetable.user.User
import pl.polsl.timetable.user.UserRepository
import java.sql.Timestamp
import java.time.Instant

@Service
@Transactional
class DefaultCourseService(
        private val userRepository: UserRepository,
        private val courseRepository: CourseRepository,
        private val categoryRepository: CategoryRepository
): CourseService {
    override fun coursesBetween(categoryId: Long, timeRange: ClosedRange<Instant>): List<JpaCourse> {
        val category = categoryRepository.findOne(categoryId) ?: throw CategoryNotFoundException(categoryId)
        return courseRepository.findByBeginTimestampBetweenAndCategoryOrderByBeginTimestampAsc(
                Timestamp.from(timeRange.start),
                Timestamp.from(timeRange.endInclusive),
                category
        )
    }

    override fun userCoursesBetween(user: User, timeRange: ClosedRange<Instant>): List<JpaCourse> {
        val jpaUser = userRepository.findOne(user.id)
        return jpaUser.selectedCategories.flatMap { category ->
            val filter = CourseFilterComposite(
                    jpaUser
                            .filters[category]
                            ?.map(::DataBasedCourseFilter)
                            ?: emptyList()
            )
            coursesBetween(category.id, timeRange).filterNot(filter)
        }
    }
}