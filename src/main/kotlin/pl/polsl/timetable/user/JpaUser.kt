package pl.polsl.timetable.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import pl.polsl.timetable.course.category.IdentifiableCategory
import pl.polsl.timetable.course.category.JpaCategory
import pl.polsl.timetable.course.filter.IdentifiableCourseFilterData
import pl.polsl.timetable.course.filter.JpaCourseFilterData
import javax.persistence.*

@Entity(name = "user")
class JpaUser(
        @Column(unique = true)
        override val email: String,

        @JsonIgnore
        override val passwordHash: String,

        @ManyToMany
        @Cascade(CascadeType.DELETE)
        @JsonIgnore
        override val selectedCategories: MutableSet<JpaCategory> = mutableSetOf(),

        @OneToMany
        @JsonIgnore
        val jpaFilters: MutableList<JpaCourseFilterData> = mutableListOf()
) : User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    override val id: Long
        @Transient
        get() = _id

    @get:Transient
    @get:JsonIgnore
    override val filters: Map<IdentifiableCategory, List<IdentifiableCourseFilterData>>
        get() {
            return jpaFilters.groupBy { it.category }
        }

}