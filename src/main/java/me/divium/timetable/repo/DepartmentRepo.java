package me.divium.timetable.repo;

import me.divium.timetable.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Optional<Department> findByName(String name);
}
