package com.example.studentsvladapp.controller;

import com.example.studentsvladapp.dto.AddStudentRequestDto;
import com.example.studentsvladapp.entity.Group;
import com.example.studentsvladapp.entity.Student;
import com.example.studentsvladapp.repository.GroupRepository;
import com.example.studentsvladapp.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;

    Group group;

    @BeforeEach
    void setUp() {
        group = new Group();
        group.setName("IT");
        groupRepository.save(group);
    }

    @AfterEach
    void clean(){
        Optional<Group> groupRes = groupRepository.findById(group.getId());
        studentRepository.findAllByGroupId(groupRes.get().getId())
                .stream()
                .map(Student::getId).forEach(id -> studentRepository.deleteById(id));
        groupRepository.deleteById(group.getId());
    }

    @Test
    void add() throws Exception {

        AddStudentRequestDto addStudentRequestDto = new AddStudentRequestDto();
        addStudentRequestDto.setGroupId(group.getId());
        addStudentRequestDto.setSurname("Gena");
        addStudentRequestDto.setStudentStatus(1);

        String studentMapper = objectMapper.writeValueAsString(addStudentRequestDto);
        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentMapper))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        assertEquals(1, groupRepository.findById(group.getId()).get().getStudents().size());
    }
}