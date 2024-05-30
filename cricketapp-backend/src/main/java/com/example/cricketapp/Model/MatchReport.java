package com.example.cricketapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchReport {

    @JsonProperty("timestamps")
    private String timestamps;

    @JsonProperty("elapsed")
    private float elapsed;

    @JsonProperty("label")
    private String label;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("threadName")
    private String threadName;

    @JsonProperty("dataType")
    private String dataType;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("failureMessage")
    private String failureMessage;

    @JsonProperty("bytes")
    private int bytes;

    @JsonProperty("sentBytes")
    private int sentBytes;

    @JsonProperty("grpThreads")
    private int grpThreads;

    @JsonProperty("allThreads")
    private int allThreads;

    @JsonProperty("URL")
    private String URL;

    @JsonProperty("Latency")
    private int Latency;

    @JsonProperty("SampleCount")
    private int SampleCount;

    @JsonProperty("ErrorCount")
    private int ErrorCount;

    @JsonProperty("IdleTime")
    private int IdleTime;

    @JsonProperty("Connect")
    private int Connect;

    @JsonProperty("Sample")
    private int Sample;

    @JsonProperty("Average")
    private int Average;

    @JsonProperty("Min")
    private int Min;

    @JsonProperty("Max")
    private int Max;

    @JsonProperty("StdDev")
    private float StdDev;

    @JsonProperty("Error")
    private float Error;

    @JsonProperty("Throughput")
    private float Throughput;

    @JsonProperty("Received")
    private float Received;

    @JsonProperty("Sent")
    private float Sent;

    @JsonProperty("AvgBytes")
    private float AvgBytes;

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public float getElapsed() {
        return elapsed;
    }

    public void setElapsed(float elapsed) {
        this.elapsed = elapsed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(int sentBytes) {
        this.sentBytes = sentBytes;
    }

    public int getGrpThreads() {
        return grpThreads;
    }

    public void setGrpThreads(int grpThreads) {
        this.grpThreads = grpThreads;
    }

    public int getAllThreads() {
        return allThreads;
    }

    public void setAllThreads(int allThreads) {
        this.allThreads = allThreads;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getLatency() {
        return Latency;
    }

    public void setLatency(int latency) {
        Latency = latency;
    }

    public int getSampleCount() {
        return SampleCount;
    }

    public void setSampleCount(int sampleCount) {
        SampleCount = sampleCount;
    }

    public int getErrorCount() {
        return ErrorCount;
    }

    public void setErrorCount(int errorCount) {
        ErrorCount = errorCount;
    }

    public int getIdleTime() {
        return IdleTime;
    }

    public void setIdleTime(int idleTime) {
        IdleTime = idleTime;
    }

    public int getConnect() {
        return Connect;
    }

    public void setConnect(int connect) {
        Connect = connect;
    }

    public int getSample() {
        return Sample;
    }

    public void setSample(int sample) {
        Sample = sample;
    }

    public int getAverage() {
        return Average;
    }

    public void setAverage(int average) {
        Average = average;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }

    public float getStdDev() {
        return StdDev;
    }

    public void setStdDev(float stdDev) {
        StdDev = stdDev;
    }

    public float getError() {
        return Error;
    }

    public void setError(float error) {
        Error = error;
    }

    public float getThroughput() {
        return Throughput;
    }

    public void setThroughput(float throughput) {
        Throughput = throughput;
    }

    public float getReceived() {
        return Received;
    }

    public void setReceived(float received) {
        Received = received;
    }

    public float getSent() {
        return Sent;
    }

    public void setSent(float sent) {
        Sent = sent;
    }

    public float getAvgBytes() {
        return AvgBytes;
    }

    public void setAvgBytes(float avgBytes) {
        AvgBytes = avgBytes;
    }

    @Override
    public String toString() {
        return "MatchReport{" +
                "timestamps='" + timestamps + '\'' +
                ", elapsed=" + elapsed +
                ", label='" + label + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", threadName='" + threadName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", success=" + success +
                ", failureMessage='" + failureMessage + '\'' +
                ", bytes=" + bytes +
                ", sentBytes=" + sentBytes +
                ", grpThreads=" + grpThreads +
                ", allThreads=" + allThreads +
                ", URL='" + URL + '\'' +
                ", Latency=" + Latency +
                ", SampleCount=" + SampleCount +
                ", ErrorCount=" + ErrorCount +
                ", IdleTime=" + IdleTime +
                ", Connect=" + Connect +
                ", Sample=" + Sample +
                ", Average=" + Average +
                ", Min=" + Min +
                ", Max=" + Max +
                ", StdDev=" + StdDev +
                ", Error=" + Error +
                ", Throughput=" + Throughput +
                ", Received=" + Received +
                ", Sent=" + Sent +
                ", AvgBytes=" + AvgBytes +
                '}';
    }
}
