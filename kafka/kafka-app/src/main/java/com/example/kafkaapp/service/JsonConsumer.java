package com.example.kafkaapp.service;

import com.example.kafkaapp.dto.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonConsumer {

    @KafkaListener(topics = "json-events", groupId = "json-group", containerFactory = "jsonKafkaListenerContainerFactory")
    public void listen(NotificationEvent event) {
        System.out.println("Received NotificationEvent: " + event);
    }
}
