package ru.hogwarts.schoolmaven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.schoolmaven.model.Faculty;

import java.util.*;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    List<Faculty> findFacultiesByColor(String color);
    List<Faculty> findFacultyByNameContainsIgnoreCaseOrColorContainsIgnoreCase(String name, String color);

}