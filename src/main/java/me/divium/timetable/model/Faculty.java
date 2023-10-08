package me.divium.timetable.model;

//import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Faculty {
    private String name;
    private List<Year> years;
}
