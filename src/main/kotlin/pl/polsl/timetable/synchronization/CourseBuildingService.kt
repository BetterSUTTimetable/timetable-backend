package pl.polsl.timetable.synchronization

import pl.polsl.timetable.course.Course
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.synchronization.scraper.TimetablePage

interface CourseBuildingService {
    fun updateCourses(category: JpaCategory, courses: List<Course>)
}