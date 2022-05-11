package com.example.studentsvladapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.PersistenceContextType.*;

@Entity
@Table(name = "university_group")
@Setter
@Getter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String name;

    @Column(name = "create_at")
    private LocalDate createGroupAt;

    @Column(name = "group_status")
    private int groupStatus;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Student> students;

    public void addStudentToGroup(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
        student.setGroup(this);
    }

}

