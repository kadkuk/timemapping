package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeMappingController {

    @Autowired
    private TimeMappingService timeMappingService;

    @PutMapping("/public/starttime")
    public void startLog(@RequestBody TimeMappingLog timeMappingLog) {
        timeMappingService.startLog(timeMappingLog);
    }

    @PutMapping("/public/stoptime")
    public void stopLog(@RequestBody TimeMappingLog timeMappingLog) {
        timeMappingService.stopLog(timeMappingLog);
    }

    @PostMapping("/public/createproject")
    public void createProject(@RequestBody TimeMappingProject timeMappingProject) {
        timeMappingService.createProject(timeMappingProject);
    }

    @PostMapping("/public/createactivity")
    public void createActivity(@RequestBody TimeMappingActivity timeMappingActivity) {
        timeMappingService.createActivity(timeMappingActivity);
    }

}
