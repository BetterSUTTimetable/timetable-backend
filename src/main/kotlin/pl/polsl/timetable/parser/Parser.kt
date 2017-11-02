package pl.polsl.timetable.parser

import java.io.BufferedReader
import java.io.IOException

interface Parser {
    @Throws(IOException::class)
    fun parse(reader: BufferedReader): List<Event>
}