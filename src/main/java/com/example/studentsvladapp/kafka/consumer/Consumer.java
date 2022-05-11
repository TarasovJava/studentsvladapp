package com.example.studentsvladapp.kafka.consumer;

import com.example.studentsvladapp.kafka.dto.MessageTinkoffDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "out-tinkoff-info", groupId = "clientId")
    public void listenerTopic(ConsumerRecord<String, MessageTinkoffDto> record) {
        System.out.println(record);
    }
}