package me.divium.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Week {
    private String name;
    private List<Day> days;
}
