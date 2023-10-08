package me.divium.timetable.service;

import me.divium.timetable.exceptions.TooSoonException;
import me.divium.timetable.model.*;
import me.divium.timetable.repo.UniversityRepo;
import me.divium.timetable.scrapper.GroupTimetableScrapperFactory;
import me.divium.timetable.scrapper.UniversityScrapperFactory;
import me.divium.timetable.scrapper.exceptions.ParserException;
import me.divium.timetable.scrapper.lib.GroupTimetableScrapper;
import me.divium.timetable.scrapper.lib.UniversityScrapper;
import me.divium.timetable.scrapper.model.group.SFaculty;
import me.divium.timetable.scrapper.model.group.SGroup;
import me.divium.timetable.scrapper.model.group.SUniversity;
import me.divium.timetable.scrapper.model.group.SYear;
import me.divium.timetable.scrapper.model.timetable.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapperService {
    private final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

    @Autowired
    UniversityRepo universityRepo;

    private String currentUniversityName = "";

    public void scrape(String universityName) {
        logger.info(String.format("Initiating scraping process for '%s' university", universityName));

        // Fix this to get only last element
         List<UniversityModel> universities = universityRepo.findAllByName(universityName);
         if (!universities.isEmpty()) {
             LocalDate current = LocalDateTime.now().toLocalDate();
             LocalDate last = universities.getLast().getScrapeTimestamp().toLocalDate();

             if (!current.isAfter(last))
                 throw new TooSoonException("Last scrape was performed recently");
         }

        try {
            logger.info(String.format("Finding scrapper for '%s' university", universityName));
            currentUniversityName = universityName;

            UniversityScrapper universityScrapper = UniversityScrapperFactory.Companion.get(universityName);
            universityScrapper.scrape();
            SUniversity sUniversity = universityScrapper.getResult();

            logger.info("List of groups and URLs successfully retrieved. Starting scraping every group...");

            UniversityModel university = UniversityModel.builder()
                    .name(universityName)
                    .faculties(sFacultiesToFaculties(sUniversity.getFaculties()))
                    .scrapeTimestamp(LocalDateTime.now())
                    .build();

            logger.info("Scraping complete. Saving to database");
            logger.info("Saving complete");

            universityRepo.save(university);
        } finally {
            currentUniversityName = "";
        }
    }

    private List<Faculty> sFacultiesToFaculties(List<SFaculty> sFaculties) {
        List<Faculty> faculties = new ArrayList<>();
        for (SFaculty sFaculty : sFaculties) {
            sFaculty.getYears();
            faculties.add(Faculty.builder()
                    .name(sFaculty.getName())
                    .years(sYearsToYears(sFaculty.getYears()))
                    .build()
            );
        }
        return faculties;
    }

    private List<Year> sYearsToYears(List<SYear> sYears) {
        List<Year> years = new ArrayList<>();
        for (SYear sYear : sYears) {
            years.add(Year.builder()
                    .number(sYear.getNumber())
                    .groups(scrapeGroups(sYear.getGroups()))
                    .build()
            );
        }
        return years;
    }

    private List<Group> scrapeGroups(List<SGroup> sGroups) {
        List<Group> groups = new ArrayList<>();
        for (SGroup sGroup : sGroups) {
            String groupName = sGroup.getName();
            String url = sGroup.getUrl();

            logger.info(String.format("Scraping group: '%s', url: '%s'", groupName, url));

            GroupTimetableScrapper timetableScrapper = GroupTimetableScrapperFactory.Companion.get(currentUniversityName, url);

            try {
                timetableScrapper.scrape();
                SGroupTimetable sGroupTimetable = timetableScrapper.getResult();
                groups.add(sGroupTimetableToGroup(sGroupTimetable));
            } catch (ParserException e) {
                logger.error(String.format("Couldn't parse group timetable: '%s', url: '%s'. Skipping", groupName, url));
                groups.add(Group.builder()
                        .name(groupName)
                        .weeks(new ArrayList<>())
                        .build()
                );
            }
        }
        return groups;
    }

    private Group sGroupTimetableToGroup(SGroupTimetable sGroupTimetable) {
        return Group.builder()
                .name(sGroupTimetable.getGroupName())
                .weeks(sWeeksToWeeks(sGroupTimetable.getWeeks()))
                .build();
    }

    private List<Week> sWeeksToWeeks(List<SWeek> sWeeks) {
        List<Week> weeks = new ArrayList<>();
        for (SWeek sWeek : sWeeks) {
            String weekName = sWeek.getName();
            List<Day> days = sDaysToDays(sWeek.getDays());
            weeks.add(Week.builder()
                    .name(weekName)
                    .days(days)
                    .build()
            );
        }
        return weeks;
    }

    private List<Day> sDaysToDays(List<SDay> sDays) {
        List<Day> days = new ArrayList<>();
        for (SDay sDay : sDays) {
            DayOfWeek dayOfWeek = convertStringToDayOfWeek(sDay.getDayOfWeek());
            List<Lesson> lessons = sLessonsToLessons(sDay.getSLessons());

            days.add(Day.builder()
                    .dayOfWeek(dayOfWeek)
                    .lessons(lessons)
                    .build()
            );
        }
        return days;
    }

    private List<Lesson> sLessonsToLessons(List<SLesson> sLessons) {
        List<Lesson> lessons = new ArrayList<>();
        for (SLesson sLesson : sLessons) {
            SLessonInfo sInfo = sLesson.getInfo();
            lessons.add(
                    Lesson.builder()
                            .name(sLesson.getName())
                            .type(sLesson.getType())
                            .time(sLesson.getTime())
                            .number(sLesson.getNumber())
                            .teacher(sInfo.getTeacher())
                            .room(sInfo.getRoom())
                            .build()
            );
        }
        return lessons;
    }

    private DayOfWeek convertStringToDayOfWeek(String str) {
        return switch (str.toLowerCase()) {
          case "понедельник" -> DayOfWeek.MONDAY;
          case "вторник" -> DayOfWeek.TUESDAY;
          case "среда" -> DayOfWeek.WEDNESDAY;
          case "четверг" -> DayOfWeek.THURSDAY;
          case "пятница" -> DayOfWeek.FRIDAY;
          case "суббота" -> DayOfWeek.SATURDAY;
          default -> DayOfWeek.SUNDAY;
        };
    }
}

