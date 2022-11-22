package ru.hogwarts.schoolmaven.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.schoolmaven.model.Student;

import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge (int age);
    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT count(id) FROM student", nativeQuery = true)
    int totalCountOfStudents();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    double averageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> lastFiveStudent();

    @Query(value = "SELECT * FROM student OFFSET 10*(1-1)", nativeQuery = true)
    Page<Student> findAll(Integer pageNumber, Integer pageSize,
                          Pageable pageable);
}
