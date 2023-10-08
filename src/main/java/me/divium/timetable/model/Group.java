package me.divium.timetable.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Group {
    private String name;
    private List<Week> weeks;
}
