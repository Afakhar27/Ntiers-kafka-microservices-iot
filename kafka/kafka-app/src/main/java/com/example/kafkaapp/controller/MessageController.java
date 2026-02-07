package com.example.kafkaapp.controller;

import com.example.kafkaapp.service.SimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final SimpleProducer simpleProducer;

    @Autowired
    public MessageController(SimpleProducer simpleProducer) {
        this.simpleProducer = simpleProducer;
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody String message) {
        simpleProducer.sendMessage("simple-messages", message);
        return "Message envoyé au topic simple-messages : " + message;
    }
}
