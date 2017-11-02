package pl.polsl.timetable.parser

import java.time.Instant

data class ParsedEvent(
        override val uid: Long,
        override val start: Instant,
        override val end: Instant,
        override val summary: String
) : Event