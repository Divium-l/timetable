package me.divium.timetable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
//import jakarta.persistence.*;
import lombok.Data;
import me.divium.timetable.enums.ScrapeResult;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Entity
//@Table(name = "scrape_info")
public class ScrapeInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String url;
    private ScrapeResult result;
    private String errorMessage;
    private LocalDateTime timestamp;
}