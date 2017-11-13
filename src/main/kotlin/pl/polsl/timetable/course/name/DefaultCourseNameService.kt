package pl.polsl.timetable.course.name

import org.springframework.stereotype.Service

@Service
class DefaultCourseNameService(
        private val courseNameRepository: CourseNameRepository
): CourseNameService {

    override fun findOrCreate(courseName: CourseName): JpaCourseName {
        return courseNameRepository
                .findByFullNameAndShortName(fullName = courseName.fullName, shortName = courseName.shortName)
                .orElse(create(courseName))

    }

    private fun create(courseName: CourseName): JpaCourseName {
        val jpaCourseName = JpaCourseName(fullName = courseName.fullName, shortName = courseName.shortName)
        return courseNameRepository.save(jpaCourseName)
    }
}