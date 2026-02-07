package com.example.kafkaapp.controller;

import com.example.kafkaapp.service.SimpleConsumer;
import com.example.kafkaapp.service.SimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final SimpleProducer simpleProducer;
    private final SimpleConsumer simpleConsumer;

    @Autowired
    public MessageController(SimpleProducer simpleProducer, SimpleConsumer simpleConsumer) {
        this.simpleProducer = simpleProducer;
        this.simpleConsumer = simpleConsumer;
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody String message) {
        simpleProducer.sendMessage("simple-messages", message);
        return "Message envoyé au topic simple-messages : " + message;
    }

    @GetMapping("/messages")
    public List<String> getReceivedMessages() {
        return simpleConsumer.getReceivedMessages();
    }
}
