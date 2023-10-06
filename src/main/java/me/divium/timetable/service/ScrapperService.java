package me.divium.timetable.service;

import me.divium.timetable.model.Faculty;
import me.divium.timetable.model.Lesson;
import me.divium.timetable.model.University;
import me.divium.timetable.repo.DepartmentRepo;
import me.divium.timetable.repo.GroupRepo;
import me.divium.timetable.repo.LessonRepo;
import me.divium.timetable.repo.UniversityRepo;
import me.divium.timetable.scrapper.exceptions.NoSuchScrapperException;
import me.divium.timetable.scrapper.lib.DepartmentScrapper;
import me.divium.timetable.scrapper.lib.GroupTimetableScrapper;
import me.divium.timetable.scrapper.model.group.SFaculty;
import me.divium.timetable.scrapper.model.group.SGroup;
import me.divium.timetable.scrapper.model.group.SYear;
import me.divium.timetable.scrapper.model.timetable.*;
import me.divium.timetable.scrapper.scrappers.HtmlRutDepartmentScrapper;
import me.divium.timetable.scrapper.scrappers.HtmlRutMobileGroupTimetableScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapperService {
    @Autowired
    private UniversityRepo universityRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private LessonRepo lessonRepo;

    private static final Map<String, String> scrapperUrls = new HashMap<>();
    static {
        scrapperUrls.put("rut", "URL");
    }

    public University scrape(String universityName) {
        DepartmentScrapper departmentScrapper = getDepartmentScrapper(universityName);
        departmentScrapper.scrape();
        List<SFaculty> sFacultyList = (List<SFaculty>) departmentScrapper.getResult();
        for (SFaculty sFaculty : sFacultyList) {
            var departmentName = sFaculty.getName();
            if (departmentRepo.findByName(departmentName).isEmpty())
                departmentRepo.save(new Faculty(departmentName));
            var years = sFaculty.getYears();
            for (var year : years) {
                year.getNumber();
                var groups = year.getGroups();
                for (var group : groups) {
                    group.getName();
                    group.getUrl();
                }
            }
        }

        return null;
    }

    private void handleDepartmentScrapeResults(List<SFaculty> departmentList) {
        for (var department : departmentList) {
            String departmentName = department.getName();
            List<SYear> yearList = department.getYears();

            for (var year : yearList) {
                byte number = year.getNumber();
                List<SGroup> groupList = year.getSGroups();

                for (var group : groupList) {
                    String groupName = group.getName();
                    String url = group.getUrl();
                    GroupTimetableScrapper scrapper = getGroupTimetableScrapper("", url);
                    scrapper.scrape();
                    SGroupTimetable result = scrapper.getResult();
                }
            }
        }
    }

    private List<Lesson> parseSGroupTimetables(List<SGroupTimetable> sGroups) {
        return null;
    }

    private List<Lesson> parseSWeeks(List<SGroupTimetable> sGroupTimetables) {
        return null;
    }

    private List<Lesson> parseSDays(List<SDay> sDays) {
        return null;
    }

    private List<Lesson> parseLessons(List<SDay> sDays) {
        List<Lesson> lessons = new ArrayList<>();
        for (var sDay : sDays) {
            String groupName = sDay.getDayOfWeek();
//            Group group = new Group();
            List<SLesson> sLessons = sDay.getSLessons();

            for (var sLesson : sLessons) {
                SLessonInfo sLessonInfo = sLesson.getInfo();
                Lesson lesson = Lesson.builder()
                        .name(sLesson.getName())
                        .type(sLesson.getType())
                        .number(sLesson.getNumber())
                        .time(sLesson.getTime())
                        .room(sLessonInfo.getRoom())
                        .teacher(sLessonInfo.getTeacher())
                        .build();
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    private void handleGroupTimetableScrapeResults() {

    }

    private DepartmentScrapper getDepartmentScrapper(String universityName) {
        String url = scrapperUrls.get("rut");
        if (url == null)
            throw new NoSuchScrapperException("Scrapper not found");

        return switch (universityName) {
            case "rut" -> new HtmlRutDepartmentScrapper(url);
            default -> throw new NoSuchScrapperException("Scrapper not found");
        };
    }

    private GroupTimetableScrapper getGroupTimetableScrapper(String universityName, String url) {
        return switch (universityName) {
            case "rut" -> new HtmlRutMobileGroupTimetableScrapper(url);
            default -> throw new NoSuchScrapperException("Scrapper not found");
        };
    }

}

