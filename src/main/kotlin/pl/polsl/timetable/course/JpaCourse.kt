package pl.polsl.timetable.course

import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.course.lecturer.JpaLecturer
import pl.polsl.timetable.course.name.JpaCourseName
import pl.polsl.timetable.course.room.JpaClassroom
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import javax.persistence.*

@Entity(name = "course")
data class JpaCourse(
        @ManyToOne
        @JoinColumn
        override val name: JpaCourseName,

        @Enumerated(EnumType.STRING)
        override val courseType: CourseType,

        private val beginTimestamp: Timestamp,

        private val durationMillis: Long,

        @ManyToMany
        override val classrooms: Set<JpaClassroom>,

        @ManyToMany
        override val lecturers: Set<JpaLecturer>,

        @ManyToOne
        @JoinColumn
        val category: JpaCategory
): Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id

    override val beginTime: Instant
        @Transient
        get() = beginTimestamp.toInstant()

    override val duration: Duration
        @Transient
        get() = Duration.ofMillis(durationMillis)


    companion object {
        fun create(
                name: JpaCourseName,
                courseType: CourseType,
                beginTime: Instant,
                duration: Duration,
                classrooms: Set<JpaClassroom>,
                lecturers: Set<JpaLecturer>,
                category: JpaCategory
        ): JpaCourse {
            return JpaCourse(
                    name,
                    courseType,
                    Timestamp.from(beginTime),
                    duration.toMillis(),
                    classrooms,
                    lecturers,
                    category
            )
        }
    }
}