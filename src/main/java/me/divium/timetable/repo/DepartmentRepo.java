package me.divium.timetable.repo;

import me.divium.timetable.model.Faculty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends CrudRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);
}
