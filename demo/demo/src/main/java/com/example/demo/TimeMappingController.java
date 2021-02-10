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

    @PutMapping("/public/activity")
    public void activityLog(@RequestBody TimeMappingLog timeMappingLog) {
        timeMappingService.activityLog(timeMappingLog);
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
