package com.example.demo.javaClasses;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeMappingLog {
    private BigDecimal logCost;
    private String activityName;
    private int userId;
    private String projectName;

    public BigDecimal getLogCost() {
        return logCost;
    }

    public void setLogCost(BigDecimal logCost) {
        this.logCost = logCost;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


}

