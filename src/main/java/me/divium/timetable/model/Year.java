package me.divium.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Year {
    private Byte number;
    private List<Group> groups;
}
