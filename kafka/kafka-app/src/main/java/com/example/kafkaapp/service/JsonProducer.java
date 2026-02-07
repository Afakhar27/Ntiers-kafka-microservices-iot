package com.example.kafkaapp.service;

import com.example.kafkaapp.dto.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JsonProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public JsonProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationEvent event) {
        kafkaTemplate.send("json-events", event);
        System.out.println("Notification sent: " + event);
    }
}
