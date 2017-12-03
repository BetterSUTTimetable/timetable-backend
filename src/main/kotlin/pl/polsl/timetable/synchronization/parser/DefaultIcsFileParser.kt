package pl.polsl.timetable.synchronization.parser

import org.springframework.stereotype.Component

import java.io.BufferedReader
import java.io.IOException
import java.time.Instant
import java.util.ArrayList

@Component
class DefaultIcsFileParser : IcsFileParser {

    @Throws(IOException::class)
    override fun parse(reader: BufferedReader): List<IcsEvent> {

        val events = ArrayList<IcsEvent>()
        var currentLine: String? = reader.readLine();

        while (currentLine != null) {
            if ("BEGIN:VEVENT" == currentLine) {
                val event = parseSingleSection(reader)
                if (event != null) {
                    events.add(event)
                }
            }
            currentLine = reader.readLine();
        }

        return events
    }

    @Throws(IOException::class)
    private fun parseSingleSection(reader: BufferedReader): IcsEvent? {
        var currentLine: String? = reader.readLine();
        var uid: Long? = null
        var start: Instant? = null
        var end: Instant? = null
        var summary: String? = null

        while (currentLine != null) {

            val lineParts = currentLine.split(":".toRegex(), 2).toTypedArray()

            if (lineParts.size > 1) {
                val keyword = lineParts[0]
                val value = lineParts[1]

                if ("UID" == keyword) {
                    uid = java.lang.Long.parseLong(value)
                } else if ("DTSTART" == keyword) {
                    start = parseToDate(value)
                } else if ("DTEND" == keyword) {
                    end = parseToDate(value)
                } else if ("SUMMARY" == keyword) {
                    if (value.length > 0) {
                        summary = value
                    }
                } else if ("END:VEVENT" == currentLine) {
                    return if (uid != null && start != null && end != null && summary != null) {
                        DefaultIcsEvent(uid, start, end, summary)
                    } else {
                        null
                    }
                }
            }

            currentLine = reader.readLine();
        }

        return null
    }

    //Funkcja parsujaca date w formacie ISO z pliku .*ics
    private fun parseToDate(s: String): Instant {
        var fixedFormat = StringBuilder(s)
                .insert(s.length - 1, "00")
                .insert(4, "-")
                .insert(7, "-")
                .insert(13, ":")
                .insert(16, ":")
                .insert(19, ".")
                .toString()

        return Instant.parse(fixedFormat)
    }
}
