package com.example.studentsvladapp.service;

import com.example.studentsvladapp.dto.request.AddGroupRequestDto;
import com.example.studentsvladapp.dto.response.GroupsResponseDto;
import com.example.studentsvladapp.dto.response.SingleGroupResponseDto;
import com.example.studentsvladapp.dto.response.StudentDto;
import com.example.studentsvladapp.entity.Group;
import com.example.studentsvladapp.entity.Student;
import com.example.studentsvladapp.repository.GroupRepository;
import com.example.studentsvladapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    static final int ACTIVE_GROUP = 1;
    static final int DONT_ACTIVE_GROUP = 0;

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    /**
     * Метод для добавления группы в базу данных
     *
     * @param groupDto
     */
    @Override
    public void add(AddGroupRequestDto groupDto) {
        if (groupDto.getName() == null) {
            throw new IllegalArgumentException("Не верно передано имя группы");
        }
        if (groupDto.getId() == null) {
            Group group = new Group();
            group.setName(groupDto.getName());
            group.setCreateGroupAt(LocalDate.now());
            group.setGroupStatus(ACTIVE_GROUP);
            groupRepository.save(group);
        } else {
            Optional<Group> byId = groupRepository.findById(groupDto.getId());
            Group gr = byId.orElseThrow(() -> {
                throw new IllegalArgumentException("Группа ID: " + groupDto.getId() + " не найдена");
            });
            Group group = byId.get();
            group.setName(groupDto.getName());
            group.setCreateGroupAt(LocalDate.now());
            groupRepository.save(group);
        }
    }

    //1.Мэппинг Dto на Entity
    //   Group group = new Group();
    //   group.setName(groupDto.getName());
    //   group.setCreatedAt(LocalDate.now());

    //2. Сохранение Entity
    //  groupRepository.save(group);
//}

    @Override
    public List<GroupsResponseDto> getAllGroups() {
        //1. Получить список всех Group
        List<Group> groups = groupRepository.findAll();

        //2. Меппинг энтити в приемлимый формат ответа т.е DTO
        // List<GroupsResponseDto> groupResponseDtos = new ArrayList<>();

        //       return groups.stream().map(e -> new GroupsResponseDto(
        //               e.getId(),
        //               e.getName(),
        //               e.getGroupStatus(),
        //               e.getStudents().size()))
        //               .collect(Collectors.toList());
        //   }

        return groups.stream().map(e -> new GroupsResponseDto()
                        .setId(e.getId())
                        .setGroupName(e.getName())
                        .setGroupStatus(e.getGroupStatus())
                        .setQuantity(e.getStudents().size()))
                .collect(Collectors.toList());
    }

    //      for (Group group : groups) {
    //          GroupsResponseDto groupResponseDto = new GroupsResponseDto();
    //          groupResponseDto.setGroupName(group.getName());
    //          groupResponseDto.setId(group.getId());
    //          groupResponseDto.setQuantity(group.getStudents().size());
    //          groupResponseDto.setGroupStatus(group.getGroupStatus());
    //          groupResponseDtos.add(groupResponseDto);
    //      }
    //      return groupResponseDtos;
    //  }

    @Override
    public SingleGroupResponseDto getGroup(Long id) {
        //1. Получить Group Id
        Optional<Group> byId = groupRepository.findById(id);
        Group gr = byId.orElseThrow(() -> {
            throw new IllegalArgumentException("Группа ID: " + id + " не найдена");
        });
        Group group = byId.get();
        SingleGroupResponseDto singleGroupResponseDto = new SingleGroupResponseDto();
        singleGroupResponseDto.setGroupName(group.getName());
        singleGroupResponseDto.setId(group.getId());
        singleGroupResponseDto.setCreateGroupAt(group.getCreateGroupAt());
        singleGroupResponseDto.setGroupStatus(group.getGroupStatus());
        //  singleGroupResponseDto.setErrorCode("0");

        Set<Student> students = group.getStudents();
        //  StudentDto studentDto = new StudentDto();
        List<StudentDto> collect = students.stream().map(e -> new StudentDto()
                        .setId(e.getId())
                        .setSurname(e.getSurname())
                        .setCreateStudentAt(e.getCreateStudentAt())
                        .setStudentStatus(e.getStudentStatus()))
                .collect(Collectors.toList());

        singleGroupResponseDto.setStudents(collect);
        return singleGroupResponseDto;
    }

    //   List<StudentDto> studentDtos = new ArrayList<>();
    //   for (Student student : students) {
    //       StudentDto studentDto = new StudentDto();
    //       studentDto.setId(student.getId());
    //       studentDto.setSurname(student.getSurname());
    //       studentDto.setCreateStudentAt(student.getCreateStudentAt());
    //       studentDto.setStudentStatus(student.getStudentStatus());
    //       studentDtos.add(studentDto);
    //   }
    //   singleGroupResponseDto.setStudents(studentDtos);
    //   return singleGroupResponseDto;
    // }

    // Метод для удаления группы из БД
    // @Override
    // public void groupDeleted(Long id) {
    //     Optional<Group> optionalGroup = groupRepository.findById(id);
    //     Group group = optionalGroup.orElseThrow(() -> {
    //         throw new IllegalArgumentException("Группа ID: " + id + " не найдена");
    //     });
    //     groupRepository.deleteById(id);
    // }

    // Метод для изменения статуса группы в БД
    @Override
    public void groupDeleted(Long id) {
        Optional<Group> byId = groupRepository.findById(id);
        Group gr = byId.orElseThrow(() -> {
            throw new IllegalArgumentException("Группа ID: " + id + " не найдена");
        });
        Group group = byId.get();
        group.setGroupStatus(DONT_ACTIVE_GROUP);
        groupRepository.save(group);
    }
}
