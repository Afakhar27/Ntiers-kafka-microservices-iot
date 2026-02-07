package com.example.kafkaapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SimpleConsumer {

    private final List<String> receivedMessages = new CopyOnWriteArrayList<>();

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }

    @KafkaListener(topics = "simple-messages", groupId = "my-group-id")
    public void listen(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        
        String log = "Message reçu: " + message + ", Partition: " + partition + ", Offset: " + offset;
        System.out.println(log);
        receivedMessages.add(0, log); // Add to beginning
        if (receivedMessages.size() > 50) {
            receivedMessages.remove(receivedMessages.size() - 1);
        }
    }
}
