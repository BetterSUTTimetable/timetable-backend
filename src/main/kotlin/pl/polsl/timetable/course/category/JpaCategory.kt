package pl.polsl.timetable.course.category

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.polsl.timetable.course.Course
import pl.polsl.timetable.course.JpaCourse
import javax.persistence.*

@Entity(name = "category")
data class JpaCategory(
        override val name: String,

        @ManyToOne
        @JoinColumn
        override val parent: JpaCategory?
) : IdentifiableCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    @JsonIgnore
    private var _id: Long = 0L

    override val id: Long
        @Transient
        get() = _id
}