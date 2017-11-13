package pl.polsl.timetable.util

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class StringToInstantConverter : Converter<String, Instant> {
    override fun convert(representation: String): Instant {
        return Instant.parse(representation)
    }
}