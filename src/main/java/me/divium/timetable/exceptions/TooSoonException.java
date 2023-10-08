package me.divium.timetable.exceptions;

public class TooSoonException extends RuntimeException {
    public TooSoonException(String message) {
        super(message);
    }
}
