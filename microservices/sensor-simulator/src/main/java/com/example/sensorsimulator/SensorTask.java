package com.example.sensorsimulator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Random;

@Component
public class SensorTask {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public SensorTask(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Scheduled(fixedRate = 5000)
    public void generateData() {
        double temperature = 15.0 + (25.0 - 15.0) * random.nextDouble();
        SensorData data = new SensorData("TEMP-001", temperature, Instant.now().toString());

        try {
            // Lecture de l'URL cible depuis une variable d'environnement ou defaut localhost
            String targetUrl = System.getenv("SIMULATOR_TARGET_URL");
            if (targetUrl == null || targetUrl.isEmpty()) {
                targetUrl = "http://localhost:8080/api/ingestion/data";
            }
            
            restTemplate.postForObject(targetUrl, data, String.class);
            System.out.println("Donnée envoyée (" + targetUrl + "): " + temperature);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi: " + e.getMessage());
        }
    }
}
