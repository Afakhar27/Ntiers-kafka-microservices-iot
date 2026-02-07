package com.example.kafkaapp.service;

import com.example.kafkaapp.dto.polymorphic.BaseEvent;
import com.example.kafkaapp.dto.polymorphic.OrderCreatedEvent;
import com.example.kafkaapp.dto.polymorphic.UserRegisteredEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PolymorphicConsumer {

    @KafkaListener(topics = "polymorphic-events", groupId = "poly-group", containerFactory = "jsonKafkaListenerContainerFactory")
    public void listen(BaseEvent event) {
        if (event instanceof UserRegisteredEvent) {
            UserRegisteredEvent userEvent = (UserRegisteredEvent) event;
            System.out.println(">>> Processing User Registration: " + userEvent.getUsername());
        } else if (event instanceof OrderCreatedEvent) {
            OrderCreatedEvent orderEvent = (OrderCreatedEvent) event;
            System.out.println(">>> Processing Order Creation: " + orderEvent.getOrderId() + " Amount: " + orderEvent.getAmount());
        } else {
            System.out.println("Received unknown event type: " + event.getClass().getSimpleName());
        }
    }
}
