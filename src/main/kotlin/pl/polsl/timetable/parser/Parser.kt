package pl.polsl.timetable.parser

import java.io.BufferedReader

interface Parser {
    fun parse(reader: BufferedReader): List<Event>
    //test gita
}