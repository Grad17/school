package ru.hogwarts.schoolmaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.repositories.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.debug("The method addFaculty is called");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("The method editFaculty is called");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("The method findFaculty is called");
        return facultyRepository.findById(id).orElse(null);
    }

    public void removeFaculty(long id) {
        logger.debug("The method removeFaculty is called");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> allFaculties() {
        logger.debug("The method allFaculties is called");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findColor(String color) {
        logger.debug("The method findColor is called");
        return facultyRepository.findFacultiesByColor(color);
    }

    public Collection<Faculty> findFacultiesByColorOrName(String nameOrColor) {
        logger.debug("The method findFacultiesByColorOrName is called");
        String name = nameOrColor;
        String color = nameOrColor;
        return facultyRepository.findFacultyByNameContainsIgnoreCaseOrColorContainsIgnoreCase(name, color);
    }

    public Collection<Student> findFacultyByStudent(long id) {
        logger.debug("The method findFacultyByStudent is called");
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null) {
            return faculty.getStudents();
        }
        return null;
    }
}