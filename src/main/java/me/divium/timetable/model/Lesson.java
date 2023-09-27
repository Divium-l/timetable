package me.divium.timetable.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "LESSONS")
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Byte number;

    private Byte weekNumber;
    private String type;
    private String room;
    private String teacher;
    private String time;

    @JoinColumn(nullable = false, name = "group_id")
    @ManyToOne(targetEntity = University.class, fetch = FetchType.LAZY)
    private Group group;
}
