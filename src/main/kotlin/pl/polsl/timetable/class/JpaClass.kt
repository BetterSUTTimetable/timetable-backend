package pl.polsl.timetable.`class`

import java.sql.Timestamp
import java.time.Duration
import java.time.Instant
import javax.persistence.*

@Entity(name = "class")
class JpaClass(
        @ManyToOne
        override val name: JpaClassName,

        @Enumerated(EnumType.STRING)
        override val classType: ClassType,

        private val beginTimestamp: Timestamp,

        private val durationMillis: Long,

        @ManyToMany
        override val classrooms: List<JpaClassroom>,

        @ManyToMany
        override val lecturers: List<JpaLecturer>
): Class {
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
        fun create(name: JpaClassName, classType: ClassType, beginTime: Instant, duration: Duration, classrooms: List<JpaClassroom>, lecturers: List<JpaLecturer>): JpaClass {
            return JpaClass(
                    name,
                    classType,
                    Timestamp.from(beginTime),
                    duration.toMillis(),
                    classrooms,
                    lecturers
            )
        }
    }
}