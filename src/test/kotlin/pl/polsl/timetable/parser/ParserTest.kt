package pl.polsl.timetable.parser

import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.StringReader
import java.time.Instant

class ParserTest {
    private val testInput = """BEGIN:VCALENDAR
PRODID:-//ATS4//Plan zajęć.//PL
VERSION:2.0
CALSCALE:GREGORIAN
METHOD:PUBLISH
X-WR-CALNAME:Plan zajęć.
X-WR-TIMEZONE:Europe/Warsaw
BEGIN:VEVENT
DTSTART:20171003T063000Z
DTEND:20171003T080000Z
DTSTAMP:20171031T110952Z
UID:20171003063000
CLASS:PUBLIC
SEQUENCE:0
STATUS:CONFIRMED
SUMMARY:*PPP (sek.4,5,6) lab AD 827 910 914
TRANSP:OPAQUE
END:VEVENT
"""

    val parser: Parser = ParserOfEvents()

    @Test
    fun singleEntryTest() {
        val event = parser.parse(BufferedReader(StringReader(testInput))).first()

        Assert.assertEquals(20171003063000L, event.uid)
        Assert.assertEquals("*PPP (sek.4,5,6) lab AD 827 910 914", event.summary)
        Assert.assertEquals(Instant.parse("2017-10-03T06:30:00.00Z"), event.start)
        Assert.assertEquals(Instant.parse("2017-10-03T08:00:00.00Z"), event.end)
    }
}