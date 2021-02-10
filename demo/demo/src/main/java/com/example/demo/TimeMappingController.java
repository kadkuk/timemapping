package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
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
}
