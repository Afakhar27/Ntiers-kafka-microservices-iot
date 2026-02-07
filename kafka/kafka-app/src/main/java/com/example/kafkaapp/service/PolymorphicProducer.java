package com.example.kafkaapp.service;

import com.example.kafkaapp.dto.polymorphic.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolymorphicProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public PolymorphicProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(BaseEvent event) {
        kafkaTemplate.send("polymorphic-events", event);
        System.out.println("Polymorphic event sent: " + event);
    }
}
