package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.Classroom
import pl.polsl.timetable.course.Lecturer

interface TimetablePage {
    val classNames: Map<String, String>
    val lecturers: Set<Lecturer>
    val classrooms: Set<Classroom>
    val icsFile: IcsFile
}