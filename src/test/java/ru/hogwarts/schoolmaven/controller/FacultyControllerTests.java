package ru.hogwarts.schoolmaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schoolmaven.controller.FacultyController;
import ru.hogwarts.schoolmaven.model.Faculty;
import ru.hogwarts.schoolmaven.repositories.FacultyRepository;
import ru.hogwarts.schoolmaven.service.FacultyService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFacultyTest() throws Exception{
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void createFacultyTest() throws Exception {
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void editFacultyTest() throws Exception {
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyByColorTest() throws Exception{
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        final String name1 = "newName1";
        final long id1 = 2;
        Faculty faculty = new Faculty(id, name, color);
        Faculty faculty1 = new Faculty(id1, name1, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        when(facultyRepository.findFacultiesByColor(eq(color))).thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color?color=" + color)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty, faculty1))));
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        final String name1 = "newName1";
        final long id1 = 2;
        Faculty faculty = new Faculty(id, name, color);
        Faculty faculty1 = new Faculty(id1, name1, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        when(facultyRepository.findAll()).thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception{
        final String name = "newName";
        final String color = "newColor";
        final long id = 1;

        final String name1 = "newName1";
        final long id1 = 2;

        Faculty faculty = new Faculty(id, name, color);
        Faculty faculty1 = new Faculty(id1, name1, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        when(facultyRepository.findFacultyByNameContainsIgnoreCaseOrColorContainsIgnoreCase(color,color)).thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color_or_name?nameOrColor=" + color)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty, faculty1))));
    }
}