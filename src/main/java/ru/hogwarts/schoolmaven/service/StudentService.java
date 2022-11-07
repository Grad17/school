package ru.hogwarts.schoolmaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        logger.debug("The method addStudent is called");
        return studentRepository.save(student);
    }

    public Student editStudents(Student student) {
        logger.debug("The method editStudent is called");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("The method findStudent is called");
        return studentRepository.findById(id).orElse(null);
    }

    public void removeStudent(long id) {
        logger.debug("The method removeStudent is called");
        studentRepository.deleteById(id);
    }

    public Collection<Student> allStudent(){
        logger.debug("The method allStudent is called");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        logger.debug("The method findByAge is called");
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeRange(int min, int max){
        logger.debug("The method findByAgeRange is called");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findStudentsFaculty(Long id){
        logger.debug("The method findStudentsFaculty is called");
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null){
            return student.getFaculty();
        }
        return null;
    }

    public int totalCountOfStudents() {
        logger.debug("The method totalCountOfStudents is called");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.debug("The method averageAgeOfStudents is called");
        return studentRepository.averageAgeOfStudents();
    }

    public Collection<Student> lastFiveStudent() {
        logger.debug("The method lastFiveStudents is called");
        return studentRepository.lastFiveStudent();
    }
}