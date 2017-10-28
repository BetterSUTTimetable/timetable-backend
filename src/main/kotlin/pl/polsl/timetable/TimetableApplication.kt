package pl.polsl.timetable

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TimetableApplication

fun main(args: Array<String>) {
    SpringApplication.run(TimetableApplication::class.java, *args)
}
