package com.example.ingestionservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class IngestionProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public IngestionProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReading(SensorData data) {
        kafkaTemplate.send("temperature-readings", data);
        System.out.println("Published to Kafka: " + data);
    }
}
