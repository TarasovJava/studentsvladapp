package com.example.studentsvladapp.dto.response;

import com.example.studentsvladapp.kafka.dto.StockDto;
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
public class StudentsResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String surname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate createStudentAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long groupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int studentStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorText;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<StockDto> stocksDto;
}
