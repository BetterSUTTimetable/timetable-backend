package pl.polsl.timetable.synchronization

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.course.*
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.course.lecturer.LecturerService
import pl.polsl.timetable.course.name.CourseNameService
import pl.polsl.timetable.course.room.ClassroomService

@Service
class DefaultCourseBuildingService(
        @Autowired
        private val lecturerService: LecturerService,

        @Autowired
        private val classroomService: ClassroomService,

        @Autowired
        private val courseRepository: CourseRepository,

        @Autowired
        private val courseNameService: CourseNameService
): CourseBuildingService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun updateCourses(category: JpaCategory, courses: List<Course>) {
        logger.trace("Updating courses database for category $category")
        for (course in courses) {
            with (course) {
                val jpaLecturers = lecturers.map { lecturerService.findOrCreate(it) }.toSet()
                val jpaClassrooms = classrooms.map { classroomService.findOrCreate(it) }.toSet()
                val jpaCourseName = courseNameService.findOrCreate(name)
                val jpaCourse = JpaCourse.create(
                        name = jpaCourseName,
                        courseType = courseType,
                        classrooms = jpaClassrooms,
                        lecturers = jpaLecturers,
                        beginTime = beginTime,
                        duration = duration,
                        category = category
                )
                courseRepository.save(jpaCourse)
            }
        }
    }
}