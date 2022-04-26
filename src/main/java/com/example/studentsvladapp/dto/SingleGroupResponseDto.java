package com.example.studentsvladapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SingleGroupResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String groupName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate createGroupAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<StudentDto> students;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int groupStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorText;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

}
