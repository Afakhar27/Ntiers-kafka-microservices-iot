package com.example.kafkaapp.dto.polymorphic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserRegisteredEvent.class, name = "user"),
        @JsonSubTypes.Type(value = OrderCreatedEvent.class, name = "order")
})
public abstract class BaseEvent {
    private UUID eventId;
    private Instant timestamp;

    public BaseEvent() {
    }

    public BaseEvent(UUID eventId, Instant timestamp) {
        this.eventId = eventId;
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "BaseEvent{" +
                "eventId=" + eventId +
                ", timestamp=" + timestamp +
                '}';
    }
}
