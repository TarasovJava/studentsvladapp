package com.example.studentsvladapp.dto.request;

import com.example.studentsvladapp.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentRequestDto {

    private Long id;
    private Long groupId;
    private String surname;
    private int studentStatus;
    private List<Stock> idStocks;

}
