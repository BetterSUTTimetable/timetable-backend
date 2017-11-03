package pl.polsl.timetable.parser;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ParserOfEvents implements Parser {

    @NotNull
    @Override
    public List<Event> parse(@NotNull BufferedReader reader) throws IOException {

        final List<Event> events = new ArrayList<>();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if ("BEGIN:VEVENT".equals(currentLine)) {
                Event event = parseSingleSection(reader);
                if (event != null) {
                    events.add(event);
                }
            }
        }

        return events;
    }

    private Event parseSingleSection(@NotNull BufferedReader reader) throws IOException {
        String currentLine;
        Long uid = null;
        Instant start = null;
        Instant end = null;
        String summary = null;

        while ((currentLine = reader.readLine()) != null) {

            final String[] lineParts = currentLine.split(":", 2);

            if (lineParts.length > 1) {
                final String keyword = lineParts[0];
                final String value = lineParts[1];

                if ("UID".equals(keyword)) {
                    uid = Long.parseLong(value);
                } else if ("DTSTART".equals(keyword)) {
                    start = parseToDate(value);
                } else if ("DTEND".equals(keyword)) {
                    end = parseToDate(value);
                } else if ("SUMMARY".equals(keyword)) {
                    if (value.length() > 0) {
                        summary = value;
                    }
                } else if ("END:VEVENT".equals(currentLine)) {
                    if (uid != null && start != null && end != null && summary != null) {
                        return new ParsedEvent(uid, start, end, summary);
                    } else {
                        return null;
                    }
                }
            }
        }

        return null;
    }

    //Funkcja parsujaca date w formacie ISO z pliku .*ics
    private Instant parseToDate(String s){
        s = new StringBuilder(s)
                .insert(s.length()-1, "00")
                .insert(4, "-")
                .insert(7, "-")
                .insert(13, ":")
                .insert(16, ":")
                .insert(19, ".")
                .toString();

        return Instant.parse(s);
    }
}
