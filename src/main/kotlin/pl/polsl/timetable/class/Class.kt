package pl.polsl.timetable.`class`

import java.time.Duration
import java.time.Instant

interface Class {
    val beginTime: Instant
    val duration: Duration
    val name: ClassName
    val classType: ClassType
    val classrooms: List<Classroom>
    val lecturers: List<Lecturer>
}

