package pl.polsl.timetable.course

import pl.polsl.timetable.course.category.Category
import pl.polsl.timetable.course.lecturer.Lecturer
import pl.polsl.timetable.course.name.CourseName
import pl.polsl.timetable.course.room.Classroom
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