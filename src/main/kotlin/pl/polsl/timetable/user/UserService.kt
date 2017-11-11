package pl.polsl.timetable.user

import java.security.Principal

interface UserService {
    fun create(userData: UserLoginData)
    fun find(principal: Principal): User
}