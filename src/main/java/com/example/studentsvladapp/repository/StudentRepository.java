package com.example.studentsvladapp.repository;

import com.example.studentsvladapp.entity.Group;
import com.example.studentsvladapp.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findAll();

    List<Student> findAllByGroupId(Long gr);

}
