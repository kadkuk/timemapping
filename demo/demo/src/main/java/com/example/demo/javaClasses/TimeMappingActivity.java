package com.example.demo.javaClasses;

import java.math.BigDecimal;

public class TimeMappingActivity {
    private String projectName;
    private String activityName;
    private BigDecimal activityHourlyRate;

    public BigDecimal getActivityHourlyRate() {
        return activityHourlyRate;
    }

    public void setActivityHourlyRate(BigDecimal activityHourlyRate) {
        this.activityHourlyRate = activityHourlyRate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
