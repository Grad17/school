package ru.hogwarts.schoolmaven.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final Object flag = new Object();

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

    public Collection<Student> allStudent(Integer pageNumber, Integer pageSize){
        logger.debug("The method allStudent is called");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return studentRepository.findAll(pageRequest).getContent();
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

    public List<String> getStudentByLetter(String letter) {
        logger.debug("The method getStudentByLetter is called");

        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(s -> s.startsWith(letter))
                .sorted(String::compareTo)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public OptionalDouble getAge(){
        logger.debug("The method getAge is called");

        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average();
    }

    public void findStudentThread1() {
        logger.debug("The method findStudentThread is called");
        doOperation1(1);
        doOperation1(69);

        new Thread(() -> {
            doOperation1(64);
            doOperation1(65);
        }).start();

        new Thread(() -> {
            doOperation1(68);
            doOperation1(67);
        }).start();
    }

    public void findStudentThread2() {
        logger.debug("The method findStudentThread1 is called");

        doOperation2(1);
        doOperation2(69);

        new Thread(() -> {
            doOperation2(64);
            doOperation2(65);
        }).start();

        new Thread(() -> {
            doOperation2(68);
            doOperation2(67);
        }).start();
    }

    public void doOperation1(long id) {
        synchronized (flag) {
            Student findStudent = studentRepository.findById(id).orElse(null);
            System.out.println("Student " + findStudent);
        }
    }

    public void doOperation2(long id) {
        synchronized (flag) {
            Student findStudent = studentRepository.findById(id).orElse(null);
            System.out.println("Student " + findStudent);
        }
    }
}