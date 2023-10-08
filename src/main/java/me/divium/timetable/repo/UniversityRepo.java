package me.divium.timetable.repo;

import me.divium.timetable.model.UniversityModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UniversityRepo extends MongoRepository<UniversityModel, String> {
    List<UniversityModel> findAllByName(String name);
}
