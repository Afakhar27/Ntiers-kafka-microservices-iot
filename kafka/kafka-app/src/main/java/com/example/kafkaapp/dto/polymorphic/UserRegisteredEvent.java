package com.example.kafkaapp.dto.polymorphic;

import java.time.Instant;
import java.util.UUID;

public class UserRegisteredEvent extends BaseEvent {
    private String username;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(UUID eventId, Instant timestamp, String username) {
        super(eventId, timestamp);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
