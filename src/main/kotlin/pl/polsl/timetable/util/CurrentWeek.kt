package pl.polsl.timetable.util

import java.time.Clock
import java.time.Instant

interface WeekCalculator {
    fun wee(clock: Clock): ClosedRange<Instant>
}