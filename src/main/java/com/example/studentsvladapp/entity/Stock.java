package com.example.studentsvladapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Stock")
@Setter
@Getter
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "id_stocks")
    private String idStocks;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Student student;
}
