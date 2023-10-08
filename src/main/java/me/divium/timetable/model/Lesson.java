package me.divium.timetable.model;

//import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lesson {
    private String name;
    private String type;
    private Byte number;
    private String time;
    private String teacher;
    private String room;
}
