package pl.polsl.timetable.user

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.polsl.timetable.course.category.IdentifiableCategory
import javax.persistence.*

@Entity(name = "user")
class JpaUser(
        @Column(unique = true)
        override val email: String,

        @JsonIgnore
        override val passwordHash: String,

        @ManyToMany
        override val selectedCategories: MutableSet<IdentifiableCategory> = mutableSetOf(),

        @ManyToMany
        override val favoriteCategories: MutableSet<IdentifiableCategory> = mutableSetOf()
) : User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private var _id: Long = 0L

    val id: Long
        @Transient
        get() = _id
}