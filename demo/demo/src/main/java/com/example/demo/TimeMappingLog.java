package com.example.demo;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeMappingLog {
    private int logId;
    private int activityID;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private Duration elapsedTime;
    private BigDecimal logCost;
    private boolean logStatus;
    private String activityName;
    private int userID;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public Duration getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Duration elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public BigDecimal getLogCost() {
        return logCost;
    }

    public void setLogCost(BigDecimal logCost) {
        this.logCost = logCost;
    }

    public boolean isLogStatus() {
        return logStatus;
    }

    public void setLogStatus(boolean logStatus) {
        this.logStatus = logStatus;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
