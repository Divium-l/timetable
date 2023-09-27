package me.divium.timetable.service;

import me.divium.timetable.repo.UniversityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimetableService {

    @Autowired
    private UniversityRepo universityRepo;

    public String getUniversityGroups(String university) {
        return "";
    }

    public String getLessonsByParameters(String university) {
        return "";

    }
}
