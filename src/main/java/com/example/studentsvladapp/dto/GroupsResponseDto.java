package com.example.studentsvladapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GroupsResponseDto {

    private Long id;
    private String groupName;
    private int groupStatus;
    private int quantity;

}
