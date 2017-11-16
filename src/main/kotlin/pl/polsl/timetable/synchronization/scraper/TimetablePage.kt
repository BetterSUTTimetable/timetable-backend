package pl.polsl.timetable.synchronization.scraper

import pl.polsl.timetable.course.room.Classroom
import pl.polsl.timetable.course.lecturer.Lecturer
import java.io.BufferedReader
import java.util.*

interface TimetablePage {
    val groupName: String
    val classNames: Map<String, String>
    val lecturers: Set<Lecturer>
    val classrooms: Set<Classroom>
    val icsFile: Optional<BufferedReader>
}