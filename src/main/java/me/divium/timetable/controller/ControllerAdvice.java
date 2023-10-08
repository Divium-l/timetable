package me.divium.timetable.controller;

import me.divium.timetable.exceptions.TooSoonException;
import me.divium.timetable.model.AppResponse;
import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException;
import me.divium.timetable.scrapper.exceptions.ParserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(TooSoonException.class)
    public ResponseEntity<AppResponse> handleScrapperNotFoundException(TooSoonException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AppResponse.builder()
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(NoSuchScrapperException.class)
    public ResponseEntity<AppResponse> handleScrapperNotFoundException(NoSuchScrapperException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AppResponse.builder()
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ParserException.class)
    public ResponseEntity<AppResponse> handleParserException(ParserException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AppResponse.builder()
                        .errorMessage("Error parsing data")
                        .build()
                );
    }
}
