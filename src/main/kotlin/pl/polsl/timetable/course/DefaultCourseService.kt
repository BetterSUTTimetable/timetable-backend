package pl.polsl.timetable.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.course.category.CategoryNotFoundException
import pl.polsl.timetable.course.category.CategoryRepository
import pl.polsl.timetable.course.filter.CourseFilterComposite
import pl.polsl.timetable.course.filter.DataBasedCourseFilter
import pl.polsl.timetable.user.User
import java.sql.Timestamp
import java.time.Instant

@Service
class DefaultCourseService(
        @Autowired
        private val courseRepository: CourseRepository,

        @Autowired
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
        val courses = user.selectedCategories.flatMap { category ->
            val filter = CourseFilterComposite(
                    user
                            .filters[category]
                            ?.map(::DataBasedCourseFilter)
                            ?: emptyList()
            )
            coursesBetween(category.id, timeRange).filterNot(filter)
        }
        return courses
    }
}