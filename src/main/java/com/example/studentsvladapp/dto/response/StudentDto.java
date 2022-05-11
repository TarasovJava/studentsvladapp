package com.example.studentsvladapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StudentDto {

    private Long id;
    private String surname;
    private LocalDate createStudentAt;
    private int studentStatus;


}
