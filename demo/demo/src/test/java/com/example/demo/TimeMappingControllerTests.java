package com.example.demo;

import com.example.demo.javaClasses.*;
import com.example.demo.securityAndErrorHandling.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class TimeMappingControllerTests {

        @Autowired
        private MockMvc mockMvc;

    @Test
    void createUserTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingUser user = new TimeMappingUser();
        user.setFirstName("JaaK");
        user.setLastName("Harakas");
        user.setEmail("harakas@ttu.ee");
        user.setPassword("jaakharakas");
        http://localhost:8080/public/createuser
        mockMvc.perform(MockMvcRequestBuilders.post("/public/createuser").contentType("application/Json")
                .content(mapper.writeValueAsString(user))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void LoginTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        Login login = new Login();
        login.setEmail("kadri_kukk@outlook.com");
        login.setPassword("pirukas");
        http://localhost:8080/public/login
        mockMvc.perform(MockMvcRequestBuilders.post("/public/login").contentType("application/Json")
                .content(mapper.writeValueAsString(login))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void createProjectTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingProject project = new TimeMappingProject();
        project.setProjectName("Travelling");
        http://localhost:8080/time/createproject
        mockMvc.perform(MockMvcRequestBuilders.post("/time/createproject").contentType("application/Json")
                .content(mapper.writeValueAsString(project))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void createProjectActivityTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingActivity timeMappingActivity = new TimeMappingActivity();
        timeMappingActivity.setProjectName("home");
        timeMappingActivity.setActivityName("washing");
        timeMappingActivity.setActivityHourlyRate(BigDecimal.valueOf(23.55));
        http://localhost:8080/time/createactivity
        mockMvc.perform(MockMvcRequestBuilders.post("/time/createactivity").contentType("application/Json")
                .content(mapper.writeValueAsString(timeMappingActivity))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void createSingleActivityTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingActivity timeMappingActivity = new TimeMappingActivity();
        timeMappingActivity.setActivityName("reading");
        timeMappingActivity.setActivityHourlyRate(BigDecimal.valueOf(99.55));
        http://localhost:8080/time/createactivity
        mockMvc.perform(MockMvcRequestBuilders.post("/time/createactivity").contentType("application/Json")
                .content(mapper.writeValueAsString(timeMappingActivity))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void toggleProjectActivityTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingLog timeMappingLog = new TimeMappingLog();
        timeMappingLog.setProjectName("home");
        timeMappingLog.setActivityName("sleeping");
        http://localhost:8080/time/toggleactivity
        mockMvc.perform(MockMvcRequestBuilders.put("/time/toggleactivity").contentType("application/Json")
                .content(mapper.writeValueAsString(timeMappingLog))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void toggleSingleActivityTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        TimeMappingLog timeMappingLog = new TimeMappingLog();
        timeMappingLog.setActivityName("babycare");
        http://localhost:8080/time/toggleactivity
        mockMvc.perform(MockMvcRequestBuilders.put("/time/toggleactivity").contentType("application/Json")
                .content(mapper.writeValueAsString(timeMappingLog))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void DataSingleActivityTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        DataSingleActivity dataSingleActivity = new DataSingleActivity();
        http://localhost:8080/time/data/activity
        mockMvc.perform(MockMvcRequestBuilders.get("/time/data/activity?activityName=babycare&startTime=2021-02-17&stopTime=2021-02-19").contentType("application/Json")
                .content(mapper.writeValueAsString(dataSingleActivity))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockCustomUser
    void DataProjectTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        DataProject dataProject = new DataProject();
        http://localhost:8080/time/data/project
        mockMvc.perform(MockMvcRequestBuilders.get("/time/data/project?projectName=sports&startTime=2021-02-11&stopTime=2021-02-20").contentType("application/Json")
                .content(mapper.writeValueAsString(dataProject))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    }
