package com.example.kafkaapp.dto;

import java.time.Instant;
import java.util.UUID;

public class NotificationEvent {
    private UUID eventId;
    private Instant timestamp;
    private String message;

    public NotificationEvent() {
    }

    public NotificationEvent(UUID eventId, Instant timestamp, String message) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "eventId=" + eventId +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
