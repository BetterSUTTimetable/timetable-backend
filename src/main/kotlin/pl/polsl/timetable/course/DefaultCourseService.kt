package pl.polsl.timetable.course

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DefaultCourseService(
        @Autowired
        private val courseRepository: CourseRepository
): CourseService {
    override fun coursesBetween(categoryId: Long, from: Instant, to: Instant): List<JpaCourse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}