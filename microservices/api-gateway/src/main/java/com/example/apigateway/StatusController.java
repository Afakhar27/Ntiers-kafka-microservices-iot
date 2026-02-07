package com.example.apigateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatusController {

    private final DiscoveryClient discoveryClient;

    public StatusController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> status = new HashMap<>();

        // API Gateway is OK if we are here
        status.put("API Gateway", "OK");

        // Check Eureka Server (if we can get services, it is up)
        try {
            discoveryClient.getServices();
            status.put("Eureka Server", "OK");
        } catch (Exception e) {
            status.put("Eureka Server", "KO");
        }

        // Check Services via Eureka
        checkService(status, "ingestion-service", "Ingestion Service");
        checkService(status, "processing-service", "Processing Service");
        
        // Sensor Simulator is not a Eureka Client, check TCP directly
        status.put("Sensor Simulator", checkTcp("sensor-simulator", 8081) ? "OK" : "KO");

        // Check Kafka Broker (TCP check)
        status.put("Kafka Broker", checkTcp("kafka", 9092) ? "OK" : "KO");

        return status;
    }

    private void checkService(Map<String, String> status, String serviceId, String displayName) {
        try {
            if (!discoveryClient.getInstances(serviceId).isEmpty()) {
                status.put(displayName, "OK");
            } else {
                status.put(displayName, "KO");
            }
        } catch (Exception e) {
            status.put(displayName, "KO");
        }
    }

    private boolean checkTcp(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
