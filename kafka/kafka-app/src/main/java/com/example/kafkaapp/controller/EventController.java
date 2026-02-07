package com.example.kafkaapp.controller;

import com.example.kafkaapp.dto.NotificationEvent;
import com.example.kafkaapp.dto.polymorphic.OrderCreatedEvent;
import com.example.kafkaapp.dto.polymorphic.UserRegisteredEvent;
import com.example.kafkaapp.service.JsonProducer;
import com.example.kafkaapp.service.PolymorphicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final JsonProducer jsonProducer;
    private final PolymorphicProducer polymorphicProducer;

    @Autowired
    public EventController(JsonProducer jsonProducer, PolymorphicProducer polymorphicProducer) {
        this.jsonProducer = jsonProducer;
        this.polymorphicProducer = polymorphicProducer;
    }

    @PostMapping("/notification")
    public String sendNotification(@RequestBody NotificationEvent event) {
        jsonProducer.sendNotification(event);
        return "Notification sent successfully";
    }

    @PostMapping("/user-registered")
    public String sendUserRegistered(@RequestBody UserRegisteredEvent event) {
        polymorphicProducer.sendEvent(event);
        return "UserRegisteredEvent sent successfully";
    }

    @PostMapping("/order-created")
    public String sendOrderCreated(@RequestBody OrderCreatedEvent event) {
        polymorphicProducer.sendEvent(event);
        return "OrderCreatedEvent sent successfully";
    }
}
