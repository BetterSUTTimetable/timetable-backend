package pl.polsl.timetable.user

interface UserService {
    fun create(userData: UserLoginData)
}