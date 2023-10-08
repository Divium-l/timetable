package me.divium.timetable.model;

//import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "universities")
public class UniversityModel {
    @Id
    private Long id;

    private LocalDateTime scrapeTimestamp;
    private String name;
    private List<Faculty> faculties;
}
