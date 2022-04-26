package com.example.studentsvladapp.repository;

import com.example.studentsvladapp.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    List<Group> findAll();


}
