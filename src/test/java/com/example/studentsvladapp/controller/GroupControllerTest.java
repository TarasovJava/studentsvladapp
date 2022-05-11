package com.example.studentsvladapp.controller;

import com.example.studentsvladapp.dto.request.AddGroupRequestDto;
import com.example.studentsvladapp.entity.Group;
import com.example.studentsvladapp.repository.GroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GroupRepository groupRepository;

    @Test
    void check_add_group() throws Exception {
        AddGroupRequestDto addGroupRequestDto = new AddGroupRequestDto();
        addGroupRequestDto.setName("IT");
        addGroupRequestDto.setGroupStatus(1);
        String requestBody = objectMapper.writeValueAsString(addGroupRequestDto); //записывает DTO в строку для передачи в запрос
        mockMvc.perform(post("/group/add") // mockMvc.perform(....) отправляет запрос в наше приложение
                        .contentType(MediaType.APPLICATION_JSON) // тип нашего запроса
                        .content(requestBody)) // строка, которую мы получили из DTO
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());// предполагаем, что приложение вернёт 200 статус

        List<Group> all = groupRepository.findAll();
        // Assertions.assertTrue работает следующим образом. Слева то что ожидаем, справа то что получаем
        // в нашем случае Assertions.assertTrue - ожидаем тру.
        // получаем из стрима тру - (all.stream().anyMatch(group -> group.getName().equals("IT")));
        // anyMatch - есть ли совпадения
        Assertions.assertTrue(all.stream().anyMatch(group -> group.getName().equals("IT")));
        Assertions.assertTrue(all.stream().anyMatch(group -> group.getGroupStatus() == 1));
    }
}
