package me.divium.timetable.controller;

import me.divium.timetable.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ScrapperController {
    @Autowired
    ScrapperService scrapperService;

    @PostMapping("scrape/perform")
    public ResponseEntity<?> performScrape(@RequestBody String university) {
        if (university == null || university.isEmpty() || university.isBlank())
            return ResponseEntity.badRequest().build();

        scrapperService.scrape(university);
        return ResponseEntity.ok().build();
    }
}
