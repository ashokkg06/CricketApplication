package com.example.cricketapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PerfMonReport {

    @JsonProperty("timestamps")
    private String timestamps;

    @JsonProperty("localhost CPU")
    private float cpu;

    @JsonProperty("localhost Memory")
    private float memory;

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public float getCpu() {
        return cpu;
    }

    public void setCpu(float cpu) {
        this.cpu = cpu;
    }

    public float getMemory() {
        return memory;
    }

    public void setMemory(float memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "PerfMonReport{" +
                "timestamps='" + timestamps + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                '}';
    }
}
