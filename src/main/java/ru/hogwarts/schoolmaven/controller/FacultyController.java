package ru.hogwarts.schoolmaven.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty addFaculty = facultyService.addFaculty(faculty);
        if (addFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.editFaculty(faculty);
        if (createFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.allFaculties());
    }

    @GetMapping("color")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam("color") String color) {
        return ResponseEntity.ok(facultyService.findColor(color));
    }

    @GetMapping("color_or_name")
    public ResponseEntity<Collection<Faculty>> findFacultiesByNameOrColor(@RequestParam("nameOrColor") String nameOrColor) {
        return ResponseEntity.ok(facultyService.findFacultiesByColorOrName(nameOrColor));
    }

    @GetMapping("/{id}/students_faculty")
    public ResponseEntity<Collection<Student>> findFacultyByStudent(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.findFacultyByStudent(id));
    }
    @GetMapping("long_name_faculty")
    public ResponseEntity<Optional<String>> getLongName(){
        return ResponseEntity.ok(facultyService.longNameFaculty());
    }
}