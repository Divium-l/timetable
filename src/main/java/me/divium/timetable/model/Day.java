package me.divium.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
@Builder
public class Day {
    private DayOfWeek dayOfWeek;
    private List<Lesson> lessons;
}
