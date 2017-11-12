package pl.polsl.timetable.synchronization

import pl.polsl.timetable.course.Course
import pl.polsl.timetable.synchronization.scraper.TimetablePage

interface CoursesBuilder {
    fun build(timetablePage: TimetablePage): List<Course>
}