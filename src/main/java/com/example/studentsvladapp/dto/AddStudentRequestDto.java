package com.example.studentsvladapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentRequestDto {

    private Long id;
    private Long groupId;
    private String surname;
    private int studentStatus;

}
