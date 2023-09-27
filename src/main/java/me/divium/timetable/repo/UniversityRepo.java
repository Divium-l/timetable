package me.divium.timetable.repo;

import me.divium.timetable.model.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepo extends CrudRepository<University, Long> {
}
