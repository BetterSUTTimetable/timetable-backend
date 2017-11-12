package pl.polsl.timetable.course

import java.time.Duration
import java.time.Instant

data class DefaultCourse(
        override val beginTime: Instant,
        override val duration: Duration,
        override val name: CourseName,
        override val courseType: CourseType,
        override val classrooms: Set<Classroom>,
        override val lecturers: Set<Lecturer>
) : Course