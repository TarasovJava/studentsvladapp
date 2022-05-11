package com.example.studentsvladapp.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockDto {
    private String ticker;
    private String name;
    private double price;
    private String errorCode;
    private String errorText;
    private Currency currency;
}