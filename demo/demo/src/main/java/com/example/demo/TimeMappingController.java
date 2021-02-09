package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeMappingController {

    @Autowired
    private TimeMappingService timeMappingService;

    //http://localhost:8085/public/createuser
    @PostMapping ("/public/createuser")
    public String createUser(@RequestBody User user) {
        return timeMappingService.createUser(user);
    }

    @PostMapping("/public/login")
    public String login(@RequestBody Login login) {
        return "";    }
}
