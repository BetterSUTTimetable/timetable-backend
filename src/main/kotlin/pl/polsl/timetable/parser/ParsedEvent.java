package pl.polsl.timetable.parser;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class ParsedEvent implements Event {

    private Long uid;
    private Instant start;
    private Instant end;
    private String summary;

    public ParsedEvent(){

    }

    public ParsedEvent(Long uid, Instant start, Instant end, String summary) {
        this.uid = uid;
        this.start = start;
        this.end = end;
        this.summary = summary;
    }

    @Override
    public long getUid() {
        return uid;
    }

    @NotNull
    @Override
    public Instant getStart() {
        return start;
    }

    @NotNull
    @Override
    public Instant getEnd() {
        return end;
    }

    @NotNull
    @Override
    public String getSummary() {
        return summary;
    }
}
