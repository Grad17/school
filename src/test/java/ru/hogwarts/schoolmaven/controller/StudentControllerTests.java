package ru.hogwarts.schoolmaven.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.schoolmaven.controller.StudentController;
import ru.hogwarts.schoolmaven.model.Student;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createStudentTest(){
        Student student = given_student_with("nameStudent", 12);
        ResponseEntity<Student> response = when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        then_student_has_been_created(response);
    }

    @Test
    public void getStudentTest(){
        Student student = given_student_with("nameStudent", 12);
        ResponseEntity<Student> createResponse = when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        then_student_has_been_created(createResponse);

        Student createStudent = createResponse.getBody();
        then_student_with_id_has_been_found(createStudent.getId(), createStudent);
    }

    @Test
    public void findStudentByAge(){
        Student student_23 = given_student_with("nameStudent1", 23);
        Student student = given_student_with("nameStudent", 12);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student_23);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "23");
        then_students_are_found_by_criteria(queryParams, student_23);
    }

    @Test
    public void findStudentByAgeBetween(){
        Student student = given_student_with("nameStudent", 12);
        Student student_23 = given_student_with("nameStudent1", 23);
        Student student_32 = given_student_with("nameStudent2", 32);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student_23);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student_32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("min", "12");
        queryParams.add("max", "23");
        then_students_are_found_by_criteria_age_between(queryParams, student, student_23);
    }

    @Test
    public void editStudentTest(){
        Student student = given_student_with("nameStudent", 12);

        ResponseEntity<Student> responseEntity = when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        then_student_has_been_created(responseEntity);
        Student createdStudent = responseEntity.getBody();

        when_updating_student(createdStudent, 32, "nameStudent2");
        then_student_has_been_update(createdStudent, 32, "nameStudent2");
    }

    @Test
    public void deleteStudentTest(){
        Student student = given_student_with("nameStudent", 12);
        ResponseEntity<Student> responseEntity = when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        then_student_has_been_created(responseEntity);
        Student createdStudent = responseEntity.getBody();

        when_deleting_student(createdStudent);
        then_student_not_found(createdStudent);
    }

    @Test
    public void getAllTest(){
        Student student = given_student_with("nameStudent", 12);
        Student student_23 = given_student_with("nameStudent1", 23);
        Student student_32 = given_student_with("nameStudent2", 32);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student_23);
        when_sending_create_student_request(getUriBuilder().build().toUri(), student_32);

        MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        then_student_with_id_has_been_found_all(expected, student, student_23, student_32);
    }

    private void when_deleting_student(Student createdStudent){
        restTemplate.delete(getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri());
    }

    private void then_student_not_found(Student createdStudent){
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> emptyRs = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(emptyRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    private Student given_student_with(String name, int age){
        return new Student(name, age);
    }

    private ResponseEntity<Student> when_sending_create_student_request(URI uri, Student student){
        return restTemplate.postForEntity(uri, student, Student.class);
    }

    private void then_student_has_been_created(ResponseEntity<Student> response){
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    private UriComponentsBuilder getUriBuilder(){
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("Localhost")
                .port(port)
                .path("/student");
    }


    private void then_student_with_id_has_been_found(Long studentId, Student student){
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);
        Assertions.assertThat(response.getBody()).isEqualTo(student);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void then_student_with_id_has_been_found_all(MultiValueMap<String, String> expected, Student... student){
        URI uri = getUriBuilder().queryParams(expected).build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(student);
    }

    private void then_students_are_found_by_criteria(MultiValueMap<String, String> queryParams, Student... students){
        URI uri = getUriBuilder().queryParams(queryParams).path("/age").build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(students);
    }

    private void then_students_are_found_by_criteria_age_between(MultiValueMap<String, String> queryParams, Student... students){
        URI uri = getUriBuilder().queryParams(queryParams).path("/age_range").build().toUri();
        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Set<Student> actualResult = response.getBody();
        resetIds(actualResult);
        Assertions.assertThat(actualResult).containsExactlyInAnyOrder(students);
    }

    private void resetIds(Collection<Student> students){
        students.forEach(it -> it.setId(null));
    }

    private void when_updating_student(Student createdStudent, int newAge, String newName){
        createdStudent.setAge(newAge);
        createdStudent.setName(newName);
        restTemplate.put(getUriBuilder().build().toUri(), createdStudent);
    }

    private void then_student_has_been_update(Student createdStudent, int newAge, String newName){
        URI uri = getUriBuilder().cloneBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> updateStudentRs = restTemplate.getForEntity(uri, Student.class);

        Assertions.assertThat(updateStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateStudentRs.getBody()).isNotNull();
        Assertions.assertThat(updateStudentRs.getBody().getAge()).isEqualTo(newAge);
        Assertions.assertThat(updateStudentRs.getBody().getName()).isEqualTo(newName);
    }
}