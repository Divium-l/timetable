package me.divium.timetable.repo;

import me.divium.timetable.model.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepo extends CrudRepository<University, Long> {
    Optional<University> findByName(String name);
}
