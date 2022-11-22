package ru.hogwarts.schoolmaven.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.repositories.FacultyRepository;
import ru.hogwarts.schoolmaven.service.FacultyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.hogwarts.schoolmaven.Constants.*;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @InjectMocks
    private FacultyService facultyService;
    @Mock
    private FacultyRepository facultyRepository;
    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;

    private List<Faculty> faculties =new ArrayList<>();

    @BeforeEach
    public void setUp(){
        facultyService = new FacultyService(facultyRepository);
        faculty1 = new Faculty(IDFACULTY1, NAMEFACULTY1, COLORFACULTY1);
        faculty2 = new Faculty(IDFACULTY2, NAMEFACULTY2, COLORFACULTY3);
        faculty3 = new Faculty(IDFACULTY3, NAMEFACULTY3, COLORFACULTY3);
        faculties.add(faculty1);
        faculties.add(faculty2);
        faculties.add(faculty3);
    }

    @Test
    public void getFacultyTest(){
        Mockito.when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.ofNullable(faculty1));
        Assertions.assertEquals(faculty1, facultyService.findFaculty(faculty1.getId()));
    }

    @Test
    public void createFacultyTest(){
        Mockito.when(facultyRepository.save(faculty2)).thenReturn(faculty2);
        Assertions.assertEquals(faculty2, facultyService.addFaculty(faculty2));
    }

    @Test
    public void editFacultyTest(){
        Faculty editedFaculty = new Faculty(IDFACULTY1, NAMESTUDENT1, COLORFACULTY3);
        Mockito.when(facultyRepository.save(editedFaculty)).thenReturn(editedFaculty);
        Assertions.assertEquals(editedFaculty, facultyService.editFaculty(editedFaculty));
    }

    @Test
    public void deleteFacultyTest(){
        facultyRepository.deleteById(faculty3.getId());
        Mockito.verify(facultyRepository, Mockito.times(1)).deleteById(faculty3.getId());

    }

    @Test
    public void findFacultiesTest(){
        List<Faculty> expected = new ArrayList<>(Arrays.asList(faculty3, faculty2));
        Mockito.when(facultyRepository.findFacultiesByColor(faculty2.getColor())).thenReturn(expected);
        Assertions.assertEquals(expected, facultyService.findColor(faculty2.getColor()));
    }

    @Test
    public void getAllFacultiesTest(){
        Mockito.when(facultyRepository.findAll()).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyService.allFaculties());
    }

    @Test
    public void findFacultiesByNameOrColorTest(){
        List<Faculty> expected = new ArrayList<>(Arrays.asList(faculty3, faculty2));
        Mockito.when(facultyRepository.findFacultyByNameContainsIgnoreCaseOrColorContainsIgnoreCase(null, faculty2.getColor())).thenReturn(expected);
        Assertions.assertEquals(expected, facultyService.findFacultiesByColorOrName(COLORFACULTY2));
    }
}