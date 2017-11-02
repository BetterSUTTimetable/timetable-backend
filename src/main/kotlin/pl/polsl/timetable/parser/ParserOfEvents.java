package pl.polsl.timetable.parser;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserOfEvents implements Parser {

    private List<Event> events = null;

    @NotNull
    @Override
    public List<Event> parse(@NotNull BufferedReader reader) throws IOException {

        //Sprawdzenie ilosci zajec w pliku
        String sCurrentLine;
        int numberOfClasses = 0;
        try {
            while((sCurrentLine = reader.readLine()) != null){
                if (Objects.equals(sCurrentLine, "BEGIN:VEVENT")){
                    numberOfClasses++;
                }
        }
        } catch (IOException e1) {
            //niech ginie
        }

        //Odczytywanie zajec z z pliku do odpowiednich list
        Long uid;
        Instant start;
        Instant end;
        String summary;

        List<Long> uidList= new ArrayList<>();
        List<Instant> startList= new ArrayList<>();
        List<Instant> endList= new ArrayList<>();
        List<String> summaryList= new ArrayList<>();

        try {
            while((sCurrentLine = reader.readLine()) != null){
                if (sCurrentLine.contains("UID:")){
                    String[] parts = sCurrentLine.split(":");
                    uid = Long.parseLong(parts[1]);
                    uidList.add(uid);
                }
                if (sCurrentLine.contains("DTSTART:")){
                    String[] parts = sCurrentLine.split(":");
                    start = Instant.parse(parts[1]);
                    startList.add(start);
                }
                if (sCurrentLine.contains("DTEND:")){
                    String[] parts = sCurrentLine.split(":");
                    end = Instant.parse(parts[1]);
                    endList.add(end);
                }
                if (sCurrentLine.contains("SUMMARY:")){
                    String[] parts = sCurrentLine.split(":");
                    summary = parts[1];
                    summaryList.add(summary);
                }
            }
        } catch (IOException e1) {
            //niech ginie
        }

        //Dodanie zajec do glownej listy
        for (int i = 0; i < numberOfClasses; i++){
            Event tmp = new ParsedEvent(
                    uidList.get(i),
                    startList.get(i),
                    endList.get(i),
                    summaryList.get(i)
            );

            events.add(tmp);
        }


        return events;
    }
}
