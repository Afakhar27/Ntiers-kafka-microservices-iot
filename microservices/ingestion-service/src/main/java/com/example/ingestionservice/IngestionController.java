package com.example.ingestionservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class IngestionController {

    private final IngestionProducer producer;
    private final List<SensorData> history = new CopyOnWriteArrayList<>();

    public IngestionController(IngestionProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/data")
    public String ingestData(@RequestBody SensorData data) {
        if (data.getTemperature() < -50 || data.getTemperature() > 100) {
            return "Invalid Temperature";
        }
        history.add(0, data);
        if (history.size() > 50) {
            history.remove(history.size() - 1);
        }
        producer.sendReading(data);
        return "Data Ingested";
    }

    @GetMapping("/data")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> response = new HashMap<>();
        response.put("readings", history);
        List<String> alerts = new ArrayList<>();
        for (SensorData d : history) {
            if (d.getTemperature() > 24.0) {
                alerts.add("Alerte " + d.getSensorId() + ": " + d.getTemperature() + "°C");
            }
        }
        response.put("alerts", alerts);
        return response;
    }
}
