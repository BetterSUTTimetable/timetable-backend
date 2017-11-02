package pl.polsl.timetable.course

import javax.persistence.*

@Entity(name = "classroom")
class JpaClassroom(
        override val room: String
) : Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id
}