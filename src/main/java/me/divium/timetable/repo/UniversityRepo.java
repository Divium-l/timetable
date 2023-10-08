package me.divium.timetable.repo;

import me.divium.timetable.model.UniversityModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepo extends MongoRepository<UniversityModel, String> {
}
