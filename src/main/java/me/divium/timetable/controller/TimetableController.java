package me.divium.timetable.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimetableController {

    @GetMapping("/timetable")
    public ResponseEntity<?> getTimetable() {

        return null;
    }
}
