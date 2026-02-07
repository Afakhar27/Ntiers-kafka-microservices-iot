package com.example.kafkaapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SimpleConsumer {

    @KafkaListener(topics = "simple-messages", groupId = "my-group-id")
    public void listen(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        
        System.out.println("Message reçu: " + message + ", Partition: " + partition + ", Offset: " + offset);
    }
}
