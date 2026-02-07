package com.example.processingservice;

public class SensorData {
    private String sensorId;
    private double temperature;
    private String timestamp;

    public SensorData() {}

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "SensorData{id='" + sensorId + "', temp=" + temperature + "}";
    }
}
