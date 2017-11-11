package pl.polsl.timetable.user

import pl.polsl.timetable.GenerateNoArg

@GenerateNoArg
data class UserLoginData(
        val email: String,
        val password: String
)