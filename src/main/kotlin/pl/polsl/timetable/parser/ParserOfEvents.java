package pl.polsl.timetable.parser;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserOfEvents implements Parser {

    private List<Event> events = new ArrayList<>();

    @NotNull
    @Override
    public List<Event> parse(@NotNull BufferedReader reader) throws IOException {

        //Zmienne pomocnicze
        String sCurrentLine;
        Long uid = null;
        Instant start = null;
        Instant end = null;
        String summary = null;

        try {
            while((sCurrentLine = reader.readLine()) != null){
                if (sCurrentLine.contains("UID:")){
                    String[] parts = sCurrentLine.split(":");
                    uid = Long.parseLong(parts[1]);
                }
                if (sCurrentLine.contains("DTSTART:")){
                    String[] parts = sCurrentLine.split(":");
                    start = changeDateFormatToISO(parts[1]);
                }
                if (sCurrentLine.contains("DTEND:")){
                    String[] parts = sCurrentLine.split(":");
                    //end = Instant.parse(parts[1]);
                    end = changeDateFormatToISO(parts[1]);
                }
                if (sCurrentLine.contains("SUMMARY:")){
                    String[] parts = sCurrentLine.split(":");
                    summary = parts[1];
                }
                if (sCurrentLine.contains("END:VEVENT")){
                    if (uid == null)
                        uid = 0L;
                    if (start == null)
                        start = Instant.parse("20000101T063000Z");
                    if (end == null)
                        end = Instant.parse("20000101T063000Z");
                    if (summary == null)
                        summary = "";
                    events.add(new ParsedEvent(uid, start, end, summary));
                    uid = null;
                    start = null;
                    end = null;
                    summary = null;
                }
            }
        } catch (IOException e1) {}

        return events;
    }

    //Funkcja poprawiajaca format dat w plikach .*ics na ISO
    private Instant changeDateFormatToISO(String s){
        String tmp = s;

        tmp = new StringBuilder(tmp).insert(4, "-").toString();
        tmp = new StringBuilder(tmp).insert(7, "-").toString();
        tmp = new StringBuilder(tmp).insert(13, ":").toString();
        tmp = new StringBuilder(tmp).insert(16, ":").toString();
        tmp = new StringBuilder(tmp).insert(19, ".").toString();
        tmp = new StringBuilder(tmp).insert(tmp.length()-1, "00").toString();

        return Instant.parse(tmp);
    }
}
