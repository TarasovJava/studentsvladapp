package com.example.studentsvladapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddGroupRequestDto {

    private Long id;
    private String name;
    private int groupStatus;

}
