package pl.polsl.timetable.synchronization.scraper

import java.io.BufferedReader
import java.net.URL
import java.nio.channels.Channels


class FetchingIcsFile(private val url: URL): IcsFile {
    override val content: BufferedReader
        get() {
            val channel = Channels.newChannel(url.openStream())
            val reader = Channels.newReader(channel, "UTF-8")
            return reader.buffered()
        }
}