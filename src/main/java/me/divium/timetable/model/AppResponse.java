package me.divium.timetable.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse {
    private String errorMessage;
    private String message;
    private Map<String, ?> data;
}
