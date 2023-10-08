package me.divium.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Year {
    private Byte number;
    private List<Group> groups;
}
