package me.divium.timetable.controller;

import me.divium.timetable.model.Lesson;
import me.divium.timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @GetMapping("/timetable")
    public ResponseEntity<?> ff() {

        return null;
    }

    @GetMapping("/timetable/{university}")
    public ResponseEntity<?> getTimetable(@PathVariable String university,
                                          @RequestParam(required = false) String teacher,
                                          @RequestParam(required = false) String room,
                                          @RequestParam(required = false) String group
    ) {
        if (teacher == null && room == null && group == null) {
            timetableService.getUniversityGroups(university);
            return null;
        }

        timetableService.getLessonsByParameters(university);

        return null;
    }

    @GetMapping("/timetable/{university}")
    public ResponseEntity<?> getGroups(@PathVariable String university) {
        timetableService.getUniversityGroups(university);

        return null;
    }

    @GetMapping("/timetable/{university}/{group}")
    public ResponseEntity<?> getTimetable(@PathVariable String university, @PathVariable String group) {
        timetableService.getUniversityGroups(university);

        return null;
    }

    @GetMapping("/timetable/{university}")
    public ResponseEntity<?> search(@PathVariable String university,
                                    @RequestParam(required = false) String teacher,
                                    @RequestParam(required = false) String room,
                                    @RequestParam(required = false) String group
    ) {
        return null;
    }
}
