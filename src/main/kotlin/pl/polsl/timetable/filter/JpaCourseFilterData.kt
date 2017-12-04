package pl.polsl.timetable.filter

import pl.polsl.timetable.course.CourseType
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.course.category.JpaCategory
import java.time.Duration
import java.time.LocalTime
import javax.persistence.*

@Entity(name = "filter")
class JpaCourseFilterData(
        @ManyToOne
        @JoinColumn
        val category: JpaCategory,

        @Enumerated(EnumType.STRING)
        override val courseType: CourseType,


        override val fullCourseName: String,

        @Enumerated(EnumType.STRING)
        override val week: Week,

        private val durationMillis: Long,

        @Column(name = "time")
        private val sqlTime: java.sql.Time
) : CourseFilterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id

    override val time: LocalTime
        @Transient
        get() = sqlTime.toLocalTime()

    override val duration: Duration
        @Transient
        get() = Duration.ofMillis(durationMillis)

    companion object {
        fun create(
                category: JpaCategory,
                courseType: CourseType,
                fullCourseName: String,
                week: Week,
                time: LocalTime,
                duration: Duration
        ): JpaCourseFilterData {
            return JpaCourseFilterData(
                    category = category,
                    courseType = courseType,
                    fullCourseName = fullCourseName,
                    week = week,
                    sqlTime = java.sql.Time.valueOf(time),
                    durationMillis = duration.toMillis()
            )
        }
    }
}