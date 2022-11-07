package ru.hogwarts.schoolmaven.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student addStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudents(student);
        if (editStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent(){
        return ResponseEntity.ok(studentService.allStudent());
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(name = "age") int age) {
        if (age >= 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("age_range")
    public ResponseEntity<Collection<Student>> findStudentsByAgeRange(@RequestParam("min") int min, @RequestParam("max") int max){
        return ResponseEntity.ok(studentService.findByAgeRange(min, max));
    }

    @GetMapping("/{id}/faculty_student")
    public ResponseEntity<Faculty> findStudentsByFaculty(@PathVariable long id){
        return ResponseEntity.ok(studentService.findStudentsFaculty(id));
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/ageAverage")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastFiveStudent")
    public ResponseEntity<Collection<Student>> lastFiveStudent() {
        return ResponseEntity.ok(studentService.lastFiveStudent());
    }

    @GetMapping("student_by_Letter/{Letter}")
    public ResponseEntity<List<String>> getStudentByLetter(@PathVariable String Letter){
        return ResponseEntity.ok(studentService.getStudentByLetter(Letter));
    }

    @GetMapping("student_age")
    public ResponseEntity<OptionalDouble> getAverageAge(){
        return ResponseEntity.ok(studentService.getAge());
    }
}