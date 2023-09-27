package me.divium.timetable.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScrapperController {
    @PostMapping("scrape/perform/{university}")
    public ResponseEntity<?> performScrape(@PathVariable String university) {
        return null;
    }

    @PostMapping("scrape/perform/all")
    public ResponseEntity<?> performScrape() {
        return null;
    }

    @GetMapping("/scrape/info")
    public ResponseEntity<?> getScrapeInfo() {
        return null;
    }
}
