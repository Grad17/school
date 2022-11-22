package ru.hogwarts.schoolmaven.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.schoolmaven.model.Student;
import ru.hogwarts.schoolmaven.repositories.StudentRepository;
import ru.hogwarts.schoolmaven.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.hogwarts.schoolmaven.Constants.*;


@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    private Student student1;
    private Student student2;
    private Student student3;

    private final List<Student> students = new ArrayList<>();
    @BeforeEach
    public void setUp(){
        studentService = new StudentService(studentRepository);
        student1 = new Student(IDSTUDENT1, NAMESTUDENT1, AGESTUDENT1);
        student2 = new Student(IDSTUDENT2, NAMESTUDENT2, AGESTUDENT2);
        student3 = new Student(IDSTUDENT3, NAMESTUDENT3, AGESTUDENT3);
        students.add(student1);
        students.add(student2);
        students.add(student3);
    }

    @Test
    public void getStudentTest(){
        Mockito.when(studentRepository.findById(student1.getId())).thenReturn(Optional.ofNullable(student1));
        Assertions.assertEquals(student1, studentService.findStudent(student1.getId()));
    }

    @Test
    public void createStudentTest(){
        Mockito.when(studentRepository.save(student2)).thenReturn(student2);
        Assertions.assertEquals(student2, studentService.addStudent(student2));
    }

    @Test
    public void editStudentTest(){
        Student editedStudent = new Student(IDSTUDENT1, NAMESTUDENT1, AGESTUDENT3);
        Mockito.when(studentRepository.save(editedStudent)).thenReturn(editedStudent);
        Assertions.assertEquals(editedStudent, studentService.editStudents(editedStudent));
    }

    @Test
    public void deleteStudentTest(){
        studentRepository.deleteById(student3.getId());
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(student3.getId());
    }

    @Test
    public void getAllStudentTest(){
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        Assertions.assertEquals(students, students);
    }

    @Test
    public void findByAgeTest(){
        ArrayList<Student> expected =new ArrayList<>();
        expected.add(student2);
        expected.add(student3);
        Mockito.when(studentRepository.findStudentByAge(12)).thenReturn(expected);
        Assertions.assertEquals(expected, studentService.findByAge(12));
    }

    @Test
    public void findStudentsByAgeRangeTest(){
        ArrayList<Student> expected =new ArrayList<>();
        expected.add(student2);
        expected.add(student3);
        Mockito.when(studentRepository.findByAgeBetween(12,20)).thenReturn(expected);
        Assertions.assertEquals(expected, studentService.findByAgeRange(12,20));
    }
}