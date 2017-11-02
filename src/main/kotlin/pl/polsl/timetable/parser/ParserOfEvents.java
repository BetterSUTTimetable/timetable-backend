package pl.polsl.timetable.parser;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ParserOfEvents implements Parser {

    private List<Event> events;

    @NotNull
    @Override
    public List<Event> parse(@NotNull BufferedReader reader) throws IOException {

        events = new ArrayList<>();

        //Zmienne pomocnicze
        String sCurrentLine;
        Long uid = null;
        Instant start = null;
        Instant end = null;
        String summary = null;

            while((sCurrentLine = reader.readLine()) != null){
                if (sCurrentLine.contains("UID:")){
                    String[] parts = sCurrentLine.split(":", 2);
                    uid = Long.parseLong(parts[1]);
                }
                if (sCurrentLine.contains("DTSTART:")){
                    String[] parts = sCurrentLine.split(":", 2);
                    start = parseDateFromFileInISOFormat(parts[1]);
                }
                if (sCurrentLine.contains("DTEND:")){
                    String[] parts = sCurrentLine.split(":", 2);
                    end = parseDateFromFileInISOFormat(parts[1]);
                }
                if (sCurrentLine.contains("SUMMARY:")){
                    String[] parts = sCurrentLine.split(":",2);
                    if(parts[1].length() == 0)
                        summary = null;
                    else
                        summary = parts[1];
                }
                if (sCurrentLine.contains("END:VEVENT")){
                    if (uid != null && start != null && end != null && summary != null)
                        events.add(new ParsedEvent(uid, start, end, summary));
                    uid = null;
                    start = null;
                    end = null;
                    summary = null;
                }
            }

        return events;
    }

    //Funkcja parsujaca date w formacie ISO z pliku .*ics
    private Instant parseDateFromFileInISOFormat(String s){
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
