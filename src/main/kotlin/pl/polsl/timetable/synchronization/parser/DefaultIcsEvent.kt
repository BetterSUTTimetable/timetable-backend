package pl.polsl.timetable.synchronization.parser

import java.time.Instant

data class DefaultIcsEvent(
        override val uid: Long,
        override val start: Instant,
        override val end: Instant,
        override val summary: String
) : IcsEvent