package pl.polsl.timetable.parser

import java.time.Instant

interface Event {
    val uid: Long
    val start: Instant
    val end: Instant
    val summary: String
}