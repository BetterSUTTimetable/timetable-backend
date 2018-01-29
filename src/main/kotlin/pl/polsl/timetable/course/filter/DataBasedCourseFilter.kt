package pl.polsl.timetable.course.filter

import pl.polsl.timetable.course.Course
import java.time.LocalTime
import java.time.ZoneId

class DataBasedCourseFilter(
        private val courseFilterData: CourseFilterData
): (Course) -> Boolean {

    override fun invoke(course: Course): Boolean {
        return with(courseFilterData) {
                course.courseType == courseType
                && course.name.fullName == fullCourseName
                //TODO: which time zone?
                && LocalTime.from(course.beginTime.atZone(ZoneId.of("UTC"))) == time
                && course.beginTime.atZone(ZoneId.of("UTC")).dayOfWeek == dayOfWeek
                && course.duration == duration
                && week(course.beginTime)
        }
    }
}