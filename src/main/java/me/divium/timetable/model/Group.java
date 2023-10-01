package me.divium.timetable.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Byte year;

    @JoinColumn(nullable = false, name = "group_id")
    @ManyToOne(targetEntity = University.class, fetch = FetchType.LAZY)
    private University university;

    @JoinColumn(nullable = false, name = "department_id")
    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
    private Department department;
}
