package pl.polsl.timetable.course

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

