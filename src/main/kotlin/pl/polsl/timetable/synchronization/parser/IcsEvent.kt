package pl.polsl.timetable.synchronization.parser

import java.time.Instant

interface IcsEvent {
    val uid: Long
    val start: Instant
    val end: Instant
    val summary: String
}