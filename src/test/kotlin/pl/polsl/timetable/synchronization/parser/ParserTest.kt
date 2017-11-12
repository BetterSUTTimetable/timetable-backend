package pl.polsl.timetable.synchronization.parser

import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.Instant

class ParserTest {
    val parser: IcsFileParser = DefaultIcsFileParser()
    val file = File(ParserTest::class.java.getResource("test.ics").toURI())

    @Test
    fun selectedEntriesTest() {
        val sampleEntries = listOf(
                DefaultIcsEvent(
                        uid = 20180123073000L,
                        start = Instant.parse("2018-01-23T07:30:00.00Z"),
                        end = Instant.parse("2018-01-23T09:00:00.00Z"),
                        summary = "ORII (gr.2) ćw JaWi 621a"
                ),
                DefaultIcsEvent(
                        uid = 20171002080000,
                        start = Instant.parse("2017-10-02T08:00:00.00Z"),
                        end = Instant.parse("2017-10-02T09:30:00.00Z"),
                        summary = "*TAI (sek.4-9) proj MBar 545"
                ),
                DefaultIcsEvent(
                        uid = 20171002100000,
                        start = Instant.parse("2017-10-02T10:00:00.00Z"),
                        end = Instant.parse("2017-10-02T11:30:00.00Z"),
                        summary = "JO ćw Wydz.Gór.SJO B327 545 424 B 327"
                )
        )
        BufferedReader(FileReader(file)).use {
            val events = parser.parse(it)

            sampleEntries.forEach {
                Assert.assertTrue(it.toString(), events.contains(it))
            }
        }
    }
}