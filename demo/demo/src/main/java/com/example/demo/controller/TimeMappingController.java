package com.example.demo.controller;

import com.example.demo.javaClasses.*;
import com.example.demo.service.TimeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class TimeMappingController {

    @Autowired
    private TimeMappingService timeMappingService;


    @PostMapping("/public/createproject")
    public String createProject(@RequestBody TimeMappingProject timeMappingProject) {
        return timeMappingService.createProject(timeMappingProject);
    }

    @PostMapping("/public/createactivity")
    public String createActivity(@RequestBody TimeMappingActivity timeMappingActivity) {
        return timeMappingService.createActivity(timeMappingActivity);
    }

    @PutMapping("/public/toggleactivity")
    public Boolean toggleActivity(@RequestBody TimeMappingLog timeMappingLog) {
        return timeMappingService.toggleActivity(timeMappingLog);
    }

    @GetMapping("public/data/activity")
    public List<DataSingleActivity> activitySummary(
            @RequestParam("activityName") String activityName,
            @RequestParam("userId") int userId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("stopTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stopTime) {
        return timeMappingService.dataSingleActivity(activityName, userId, startTime, stopTime);
    }

    @GetMapping("public/data/project")
    public List<DataProject> projectSummary(
            @RequestParam("projectName") String projectName,
            @RequestParam("userId") int userId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("stopTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stopTime) {
        return timeMappingService.dataProject(projectName, userId, startTime, stopTime);
    }
}
