package me.divium.timetable.model;

//import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "universities")
public class UniversityModel {
    @Id
    private String id;

    private LocalDateTime scrapeTimestamp;
    private String name;
    private List<Faculty> faculties;
}
