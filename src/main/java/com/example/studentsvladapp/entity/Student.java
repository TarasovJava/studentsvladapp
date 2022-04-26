package com.example.studentsvladapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
@Setter
@Getter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "create_at")
    private LocalDate createStudentAt;

    @Column(name = "student_status")
    private int studentStatus;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", createStudentAt=" + createStudentAt +
                ", studentStatus=" + studentStatus +
                ", group=" + group +
                '}';
    }
}
