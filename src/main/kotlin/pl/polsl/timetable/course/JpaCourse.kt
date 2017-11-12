package pl.polsl.timetable.course

import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import javax.persistence.*

@Entity(name = "course")
class JpaCourse(
        @ManyToOne
        override val name: JpaCourseName,

        @Enumerated(EnumType.STRING)
        override val courseType: CourseType,

        private val beginTimestamp: Timestamp,

        private val durationMillis: Long,

        @ManyToMany
        override val classrooms: Set<JpaClassroom>,

        @ManyToMany
        override val lecturers: Set<JpaLecturer>
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
        fun create(name: JpaCourseName, courseType: CourseType, beginTime: Instant, duration: Duration, classrooms: Set<JpaClassroom>, lecturers: Set<JpaLecturer>): JpaCourse {
            return JpaCourse(
                    name,
                    courseType,
                    Timestamp.from(beginTime),
                    duration.toMillis(),
                    classrooms,
                    lecturers
            )
        }
    }
}