package com.example.demo.controller;

import com.example.demo.javaClasses.*;
import com.example.demo.service.TimeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class TimeMappingController {

    @Autowired
    private TimeMappingService timeMappingService;


    @PostMapping("/time/createproject")
    public String createProject(@RequestBody TimeMappingProject timeMappingProject, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.createProject(timeMappingProject, userDetails.getId());
    }

    @PostMapping("/time/createactivity")
    public String createActivity(@RequestBody TimeMappingActivity timeMappingActivity, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.createActivity(timeMappingActivity, userDetails.getId());
    }

    @PutMapping("/time/toggleactivity")
    public Boolean toggleActivity(@RequestBody TimeMappingLog timeMappingLog, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.toggleActivity(timeMappingLog, userDetails.getId());
    }

    @GetMapping("time/data/activity")
    public List<DataSingleActivity> activitySummary(
            @RequestParam("activityId") int activityId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("stopTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stopTime,
            Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.dataSingleActivity(activityId, userDetails.getId(), startTime, stopTime);
    }

    @GetMapping("time/data/project")
    public List<DataProject> projectSummary(
            @RequestParam("projectId") int projectId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("stopTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stopTime,
            Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.dataProject(projectId, userDetails.getId(), startTime, stopTime);
    }

    @GetMapping("time/data/activitieslist")
    public List<ListActivities> activitiesList(
            Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.listActivities(userDetails.getId());
    }

    @GetMapping("time/data/projectslist")
    public List<ListProject> projectList(
            Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return timeMappingService.listProject(userDetails.getId());
    }
}
