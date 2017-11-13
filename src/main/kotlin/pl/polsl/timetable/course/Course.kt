package pl.polsl.timetable.course

import pl.polsl.timetable.course.category.Category
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.name.CourseName
import pl.polsl.timetable.course.room.Classroom
import java.time.Duration
import java.time.Instant

interface Course {
    val beginTime: Instant
    val duration: Duration
    val name: CourseName
    val courseType: CourseType
    val classrooms: Set<Classroom>
    val lecturers: Set<Lecturer>
}

