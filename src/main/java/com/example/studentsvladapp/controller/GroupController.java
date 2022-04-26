package com.example.studentsvladapp.controller;

import com.example.studentsvladapp.dto.AddGroupRequestDto;
import com.example.studentsvladapp.dto.GroupsResponseDto;
import com.example.studentsvladapp.dto.SingleGroupResponseDto;
import com.example.studentsvladapp.service.GroupService;
import com.example.studentsvladapp.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/group")
public class GroupController {

    private final StudentService studentService;
    private final GroupService groupService;

    public GroupController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @PostMapping(path = "/add")
    public String add(@RequestBody AddGroupRequestDto groupDto) {
        try {
            groupService.add(groupDto);
            return "группа добавлена";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update")
    public String groupUpdate(@RequestBody AddGroupRequestDto requestDto) {
        try {
            groupService.add(requestDto);
            return "Группа c id: " + requestDto.getId() + " изменена";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "/get")
    public List<GroupsResponseDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping(path = "/get/{id}")
    public SingleGroupResponseDto getGroup(@PathVariable Long id) {
        try {
            return groupService.getGroup(id);
        } catch (IllegalArgumentException e) {
            return new SingleGroupResponseDto()
                    .setErrorText(e.getMessage())
                    .setErrorCode("0");
        }
    }
   // Метод для удаления группы из БД.
   // @DeleteMapping("/delete/{id}")
   // public String delete(@PathVariable Long id) {
   //     try {
   //         groupService.groupDeleted(id);
   //         return "Группа " + id + " удалена!";
   //     } catch (IllegalArgumentException e) {
   //         return e.getMessage();
   //     }
   // }

   // Метод для изменения статуса группы в БД
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            groupService.groupDeleted(id);
            studentService.deletedAllStudent(id);
            return "Статус у группы c id " + id + " изменен на 0, " +
                    "а так же у студентов входящих в эту группу";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}



