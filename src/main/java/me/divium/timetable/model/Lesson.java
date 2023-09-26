package me.divium.timetable.model;

import lombok.Data;

@Data
public class Lesson {
    Long id;
    String name;
    Byte number;
    String type;
    String room;
    String teacher;
    String time;
}
