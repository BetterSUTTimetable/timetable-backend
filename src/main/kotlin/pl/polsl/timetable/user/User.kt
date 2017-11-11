package pl.polsl.timetable.user

interface User {
    val email: String
    val passwordHash: String
}