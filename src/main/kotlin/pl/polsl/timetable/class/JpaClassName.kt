package pl.polsl.timetable.`class`

import javax.persistence.*

@Entity(name = "class_name")
class JpaClassName(
        override val fullName: String,

        override val shortName: String
) : ClassName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id

}