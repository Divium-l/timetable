package me.divium.timetable.enums;

import lombok.Getter;

@Getter
public enum ScrapeResult {
    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL");

    private final int value;
    private final String name;

    ScrapeResult(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
