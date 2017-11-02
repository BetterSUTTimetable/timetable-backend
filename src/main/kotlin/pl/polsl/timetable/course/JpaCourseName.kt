package pl.polsl.timetable.course

import javax.persistence.*

@Entity(name = "course_name")
class JpaCourseName(
        override val fullName: String,

        override val shortName: String
) : CourseName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id

}