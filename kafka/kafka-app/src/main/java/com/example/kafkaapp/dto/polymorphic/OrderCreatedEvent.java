package com.example.kafkaapp.dto.polymorphic;

import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent extends BaseEvent {
    private String orderId;
    private double amount;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(UUID eventId, Instant timestamp, String orderId, double amount) {
        super(eventId, timestamp);
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                "} " + super.toString();
    }
}
