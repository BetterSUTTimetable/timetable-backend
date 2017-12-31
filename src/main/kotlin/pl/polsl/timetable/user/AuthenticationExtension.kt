package pl.polsl.timetable.user

import org.springframework.security.core.Authentication

fun Authentication.user() = (this.principal as CustomUserDetails)