package pl.polsl.timetable.filter

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR
import java.time.temporal.WeekFields

enum class Week: (Instant) -> Boolean {
    EvenWeek {
        override fun invoke(instant: Instant): Boolean {
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).get(WEEK_OF_WEEK_BASED_YEAR) % 2 == 1
        }
    },

    OddWeek {
        override fun invoke(instant: Instant): Boolean {
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).get(WEEK_OF_WEEK_BASED_YEAR) % 2 == 0
        }
    },

    EveryWeek {
        override fun invoke(instant: Instant): Boolean = true
    }
}

