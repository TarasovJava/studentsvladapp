package com.example.studentsvladapp.service;

import com.example.studentsvladapp.dto.request.AddGroupRequestDto;
import com.example.studentsvladapp.dto.response.GroupsResponseDto;
import com.example.studentsvladapp.dto.response.SingleGroupResponseDto;

import java.util.List;

public interface GroupService {
    void add(AddGroupRequestDto groupDto);

    List<GroupsResponseDto> getAllGroups();

    SingleGroupResponseDto getGroup(Long id);

    void groupDeleted(Long id);

}
