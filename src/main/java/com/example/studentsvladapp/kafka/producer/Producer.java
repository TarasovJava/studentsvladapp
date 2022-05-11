package com.example.studentsvladapp.kafka.producer;

import com.example.studentsvladapp.kafka.dto.MessageTinkoffDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, MessageTinkoffDto> kafkaTemplate;

    public void send(MessageTinkoffDto message, String topic) {
        ListenableFuture<SendResult<String, MessageTinkoffDto>> sendMessage =
                kafkaTemplate.send(topic, message);
        kafkaTemplate.flush();
        System.out.println("Сообщение отправлено: " + sendMessage.isDone());
    }
}