package com.example.studentsvladapp.service;

import com.example.studentsvladapp.dto.request.AddStudentRequestDto;
import com.example.studentsvladapp.dto.response.StudentsResponseDto;
import com.example.studentsvladapp.entity.Group;
import com.example.studentsvladapp.entity.Student;
import com.example.studentsvladapp.kafka.dto.MessageTinkoffDto;
import com.example.studentsvladapp.kafka.dto.StockDto;
import com.example.studentsvladapp.kafka.producer.Producer;
import com.example.studentsvladapp.repository.GroupRepository;
import com.example.studentsvladapp.repository.StudentRepository;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    static final int STUDENT_ACTIVE = 1;
    static final int STUDENT_DONT_ACTIVE = 0;


    private ConsumerFactory<String, MessageTinkoffDto> consumerFactory;
    private final Producer producer;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;


    public StudentServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository, Producer producer) {
        this.groupRepository = groupRepository;
        this.producer = producer;
        this.studentRepository = studentRepository;
    }

    @Override
    public void add(AddStudentRequestDto addStudentRequestDto) {
        if (addStudentRequestDto.getSurname() == null) {
            throw new IllegalArgumentException("НЕ ПРАВИЛЬНО ПЕРЕДАНО ИМЯ");
        }
        if (addStudentRequestDto.getGroupId() != null) {
            //1. Получить группу в которую будем добавлять студента
            Optional<Group> optionalGroup = groupRepository.findById(addStudentRequestDto.getGroupId());
            Group group = optionalGroup.orElseThrow(() -> {
                throw new IllegalArgumentException("Группа ID: " + addStudentRequestDto.getGroupId() + " не найдена");
            });
            //2. Наполняем студента и добавляем его в полученную группу
            Student student = new Student();
            student.setSurname(addStudentRequestDto.getSurname());
            student.setGroup(group);
            student.setCreateStudentAt(LocalDate.now());
            student.setStudentStatus(STUDENT_ACTIVE);
            student.setIdStocks(addStudentRequestDto.getIdStocks());
            // group.addStudentToGroup(student);
            //3. Сохраняем студента
            studentRepository.save(student);
        }
        // Логика для изменения студента
        if (addStudentRequestDto.getId() != null) {
            Optional<Student> byId = studentRepository.findById(addStudentRequestDto.getId());
            Student st = byId.orElseThrow(() -> {
                throw new IllegalArgumentException("студент c ID: " + addStudentRequestDto.getId() + " не найден");
            });
            Student student = byId.get();
            student.setSurname(addStudentRequestDto.getSurname());
            if (addStudentRequestDto.getIdStocks() != null){
                student.setIdStocks(addStudentRequestDto.getIdStocks());
            }
            studentRepository.save(student);
        }
    }

    // Метод для удаления группы из БД
    //@Override
    //public void deletedStudent(Long id) {
    //    Optional<Student> optionalStudent = studentRepository.findById(id);
    //    Student student = optionalStudent.orElseThrow(() -> {
    //        throw new IllegalArgumentException("студент ID: " + id + " не найден");
    //    });
    //    studentRepository.deleteById(id);
    //}

    // Метод для изменения статуса группы в БД
    @Override
    public void deletedStudent(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        Student st = byId.orElseThrow(() -> {
            throw new IllegalArgumentException("студент c ID: " + id + " не найден");
        });
        Student student = byId.get();
        student.setStudentStatus(STUDENT_DONT_ACTIVE);
        studentRepository.save(student);
    }

    @Override
    public List<StudentsResponseDto> getAllStudents() {
        List<Student> all = studentRepository.findAll();
        return all.stream().map(e -> new StudentsResponseDto()
                        .setId(e.getId())
                        .setSurname(e.getSurname())
                        .setCreateStudentAt(e.getCreateStudentAt())
                        .setGroupId(e.getGroup().getId())
                        .setStudentStatus(e.getStudentStatus())
                        .setStocksDto(getStockStudent(e)))
                .collect(Collectors.toList());
    }

    private List<StockDto> getStockStudent(Student student) {
        var stocksDto = new ArrayList<StockDto>();
        student.getIdStocks().forEach(stock -> {
            stocksDto.add(new StockDto().setTicker(stock.getIdStocks()));
        });
        var messageTinkoffDto = new MessageTinkoffDto()
                .setRequestId(student.getId())
                .setStocksDto(stocksDto);
        producer.send(messageTinkoffDto, "in-tinkoff-stocks");

        Consumer<String, MessageTinkoffDto> consumer = consumerFactory.createConsumer();
        consumer.subscribe(Collections.singleton("out-tinkoff-result"));
        ConsumerRecords<String, MessageTinkoffDto> records = consumer.poll(Duration.ofSeconds(20));

        return records.iterator().next().value().getStocksDto();
    }

    //  List<StudentsResponseDto> studentsResponseDtos = new ArrayList<>();
    //  for (Student student : all) {
    //      StudentsResponseDto studentsResponseDto = new StudentsResponseDto();
    //      studentsResponseDto.setId(student.getId());
    //      studentsResponseDto.setSurname(student.getSurname());
    //      studentsResponseDto.setCreateStudentAt(student.getCreateStudentAt());
    //      studentsResponseDto.setGroupId(student.getGroup().getId());
    //      studentsResponseDto.setStudentStatus(student.getStudentStatus());
    //      studentsResponseDtos.add(studentsResponseDto);
    //  }
    //  return studentsResponseDtos;
    // }

    @Override
    public void deletedAllStudent(Long groupId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        byId.orElseThrow(() -> {
            throw new IllegalArgumentException("Группа ID: " + groupId + " не найдена");
        });
        Group group = byId.get();
        Set<Student> students = group.getStudents();
        for (Student st : students) {
            st.setStudentStatus(STUDENT_DONT_ACTIVE);
            studentRepository.save(st);
        }
    }

    @Override
    public List<StudentsResponseDto> getStudentsByGroupId(Long groupId) {
        List<Student> all = studentRepository.findAllByGroupId(groupId);
        if (all.size() == 0) {
            throw new IllegalArgumentException("студенты c запрашиваемой группы не найдены");
        }
        return all.stream().map(e -> new StudentsResponseDto()
                        .setId(e.getId())
                        .setSurname(e.getSurname())
                        .setStudentStatus(e.getStudentStatus())
                        .setCreateStudentAt(e.getCreateStudentAt()))
                .collect(Collectors.toList());

        //  List<StudentsResponseDto> studentsResponseDtos = new ArrayList<>();
        //  for (Student student : all) {
        //      StudentsResponseDto studentsResponseDto = new StudentsResponseDto();
        //      studentsResponseDto.setId(student.getId());
        //      studentsResponseDto.setSurname(student.getSurname());
        //      studentsResponseDto.setStudentStatus(student.getStudentStatus());
        //      studentsResponseDto.setCreateStudentAt(student.getCreateStudentAt());
        //      studentsResponseDtos.add(studentsResponseDto);
        //  }
        //  return studentsResponseDtos;
    }
}

