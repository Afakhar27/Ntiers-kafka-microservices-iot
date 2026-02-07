package com.example.processingservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessingConsumer {

    @KafkaListener(topics = "temperature-readings", groupId = "processing-group")
    public void process(SensorData data) {
        System.out.println("Processing received: " + data);
        if (data.getTemperature() > 24.0) {
            System.err.println("ALERTE CRITIQUE : Température élevée détectée sur le capteur " + data.getSensorId() + " : " + data.getTemperature() + "°C");
        }
    }
}
