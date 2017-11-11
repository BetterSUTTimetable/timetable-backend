package pl.polsl.timetable.parser

import java.io.BufferedReader
import java.io.IOException

interface IcsFileParser {
    @Throws(IOException::class)
    fun parse(reader: BufferedReader): List<IcsEvent>
}