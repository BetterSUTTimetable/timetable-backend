package pl.polsl.timetable.scraper

import pl.polsl.timetable.course.Classroom
import pl.polsl.timetable.course.Lecturer

interface TimetablePage {
    val classNames: Map<String, String>
    val lecturers: Map<String, Lecturer>
    val classrooms: Map<String, Classroom>
}