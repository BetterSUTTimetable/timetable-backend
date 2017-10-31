package pl.polsl.timetable.`class`

import javax.persistence.*

@Entity(name = "lecturer")
class JpaLecturer(
        override val fullName: String,

        override val shortName: String
) : Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id
}