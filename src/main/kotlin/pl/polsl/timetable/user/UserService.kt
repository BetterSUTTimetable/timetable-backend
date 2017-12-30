package pl.polsl.timetable.user

import pl.polsl.timetable.course.category.IdentifiableCategory
import java.security.Principal

interface UserService {
    fun create(userData: UserLoginData)
    fun find(principal: Principal): User
    fun addSelectedCategory(principal: Principal, categoryId: Long)
    fun removeSelectedCategory(principal: Principal, categoryId: Long)
    fun seletedCategory(principal: Principal, categoryId: Long): IdentifiableCategory
}