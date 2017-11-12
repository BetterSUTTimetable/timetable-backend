package pl.polsl.timetable.synchronization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.polsl.timetable.course.ClassroomRepository
import pl.polsl.timetable.course.CourseNameRepository
import pl.polsl.timetable.course.CourseRepository
import pl.polsl.timetable.course.LecturerRepository
import pl.polsl.timetable.synchronization.scraper.TimetablePage

@Service
class DefaultCourseBuildingService(
        @Autowired
        private val lecturerRepository: LecturerRepository,

        @Autowired
        private val classroomRepository: ClassroomRepository,

        @Autowired
        private val courseRepository: CourseRepository,

        @Autowired
        private val courseNameRepository: CourseNameRepository
): CourseBuildingService {

    override fun insertPageData(page: TimetablePage) {

    }
}