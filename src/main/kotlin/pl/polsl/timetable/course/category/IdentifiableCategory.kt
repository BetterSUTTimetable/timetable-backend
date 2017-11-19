package pl.polsl.timetable.course.category

import com.fasterxml.jackson.annotation.JsonIgnore

interface IdentifiableCategory: Category {
    val id: Long
    override val parent: IdentifiableCategory?
        @JsonIgnore
        get() = null
}