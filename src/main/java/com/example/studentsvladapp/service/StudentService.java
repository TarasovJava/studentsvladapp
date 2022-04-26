package com.example.studentsvladapp.service;

import com.example.studentsvladapp.dto.AddStudentRequestDto;
import com.example.studentsvladapp.dto.StudentsResponseDto;

import java.util.List;

public interface StudentService {
    public void add(AddStudentRequestDto addStudentRequestDto);

    public void deletedStudent(Long id);

    public List<StudentsResponseDto> getAllStudents();

    void deletedAllStudent(Long groupId);

    List<StudentsResponseDto> getStudentsByGroupId(Long groupId);
}
