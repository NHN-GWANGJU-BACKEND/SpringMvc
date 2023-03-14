package com.nhnacademy.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class StudentJsonControllerTest {
    private StudentRepository studentRepository;
    private MockMvc mockMvc;
    private StudentRegisterRequest studentModel;
    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;



    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentJsonController(studentRepository))
                .build();

        studentModel = new StudentRegisterRequest("test2", "b@b.com", 70, "bad");

        objectMapper = new ObjectMapper();
        xmlMapper = new XmlMapper();
    }


    @Test
    void viewStudent() throws Exception {
        long studentID = 2;

        Student student = Student.create("test", "a@ab.com", 50, "good");
        student.setId(studentID);

        when(studentRepository.getStudent(studentID)).thenReturn(student);

        mockMvc.perform(get("/students/{studentID}", studentID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("a@ab.com"))
                .andExpect(jsonPath("$.score").value(50))
                .andExpect(jsonPath("$.comment").value("good"))
                .andDo(print())
        ;
    }

    @Test
    void createStudentJson() throws Exception {
        Student registerStudent = new Student("test2", "b@b.com", 70, "bad");
        registerStudent.setId(0);

        when(studentRepository.register(anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(registerStudent);

        mockMvc.perform(post("/students")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(studentModel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.email").value("b@b.com"))
                .andExpect(jsonPath("$.score").value(70))
                .andExpect(jsonPath("$.comment").value("bad"))
                .andDo(print());
    }

    @Test
    void createStudentXml() throws Exception {
        mockMvc.perform(post("/students")
                        .accept(MediaType.APPLICATION_XML)
                        .characterEncoding("UTF-8")
                        .content(xmlMapper.writeValueAsString(studentModel))
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void modifyStudent() throws Exception {
        long studentID = 0;

        Student modifyStudent = new Student("test2", "b@b.com", 70, "bad");
        modifyStudent.setId(studentID);

        when(studentRepository.modify(anyLong(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(modifyStudent);

        mockMvc.perform(put("/students/{studentID}", studentID)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(studentModel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.email").value("b@b.com"))
                .andExpect(jsonPath("$.score").value(70))
                .andExpect(jsonPath("$.comment").value("bad"))
                .andDo(print());
    }
}