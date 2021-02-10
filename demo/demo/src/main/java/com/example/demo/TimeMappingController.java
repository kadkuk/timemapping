package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("public/data")
    public List<ActivityHoursCosts> activitySummary(@RequestParam("activityName") String activityName, @RequestParam("userId") int userId) {
        return timeMappingService.activityHoursCosts(activityName, userId);
    }

}
