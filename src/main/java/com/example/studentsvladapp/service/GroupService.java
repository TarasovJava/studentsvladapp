package com.example.studentsvladapp.service;

import com.example.studentsvladapp.dto.AddGroupRequestDto;
import com.example.studentsvladapp.dto.GroupsResponseDto;
import com.example.studentsvladapp.dto.SingleGroupResponseDto;
import com.example.studentsvladapp.entity.Group;

import java.util.List;

public interface GroupService {
    void add(AddGroupRequestDto groupDto);

    List<GroupsResponseDto> getAllGroups();

    SingleGroupResponseDto getGroup(Long id);

    void groupDeleted(Long id);

}
