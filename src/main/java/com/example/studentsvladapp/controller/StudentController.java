package com.example.studentsvladapp.controller;

import com.example.studentsvladapp.dto.AddGroupRequestDto;
import com.example.studentsvladapp.dto.AddStudentRequestDto;
import com.example.studentsvladapp.dto.GroupsResponseDto;
import com.example.studentsvladapp.dto.StudentsResponseDto;
import com.example.studentsvladapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get")
    public List<StudentsResponseDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/get/groupId/{groupId}")
    public List<StudentsResponseDto> getStudentsByGroupId(@PathVariable Long groupId){
        return studentService.getStudentsByGroupId(groupId);
    }

    @PostMapping(path = "/add")
    public String add(@RequestBody AddStudentRequestDto addStudentRequestDto) {
        try {
            studentService.add(addStudentRequestDto);
            return "Студент добавлен";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update")
    public String studentUpdate(@RequestBody AddStudentRequestDto addStudentRequestDto) {
        try {
            studentService.add(addStudentRequestDto);
            return "Студент с id: " + addStudentRequestDto.getId() + " изменён";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    // Метод для удаления группы из БД
    // @DeleteMapping(path = "/delete/{id}")
    // public String deletedStudent(@PathVariable Long id) {
    //     try {
    //         studentService.deletedStudent(id);
    //         return "Студент c ID: " + id + " удален";
    //     } catch (IllegalArgumentException e) {
    //         return e.getMessage();
    //     }
    // }

    // Метод для изменения статуса группы в БД
    @DeleteMapping(path = "/delete/{id}")
    public String deletedStudent(@PathVariable Long id) {
        try {
            studentService.deletedStudent(id);
            return "Статус у студента c ID: " + id + " изменен на 0";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}


