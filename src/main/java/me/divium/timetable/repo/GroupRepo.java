package me.divium.timetable.repo;

import me.divium.timetable.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends CrudRepository<Group, Long> {
    @Query("SELECT * FROM groups WHERE name = :name")
    Optional<Group> findByName(@Param("name") String name);
}
