package com.example.demo.service;

import com.example.demo.javaClasses.*;
import com.example.demo.repository.TimeMappingRepository;
import com.example.demo.securityAndErrorHandling.TimeMappingExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class TimeMappingService {

    @Autowired
    private TimeMappingRepository timeMappingRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(TimeMappingUser user) {
        try {
            timeMappingRepository.createUser(user.getFirstName(), user.getLastName(), user.getEmail(),
                    passwordEncoder.encode(user.getPassword()));
            return "User created!";
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this email already exists.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeMappingExceptions("Email cannot be empty");
        } catch (IllegalArgumentException e) {
            throw new TimeMappingExceptions("Password cannot be empty");
        }
    }

    public String createProject(TimeMappingProject timeMappingProject) {
        try {
            int userId = timeMappingRepository.requestUserId(timeMappingProject.getUserId());
            timeMappingRepository.createProject(userId, timeMappingProject.getProjectName());
            return "Project created.";
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this project already exists.");
        } catch (EmptyResultDataAccessException e) {
            throw new TimeMappingExceptions("User does not exist. Please create your user.");
        }
    }

    public String createActivity(TimeMappingActivity timeMappingActivity) {
        try {
            int userId = timeMappingRepository.requestUserId(timeMappingActivity.getUserId());
            if (timeMappingActivity.getProjectName() == null) {
                timeMappingRepository.createIndependentActivity(userId,
                        timeMappingActivity.getActivityName(),
                        timeMappingActivity.getActivityHourlyRate());
            } else {
                int projectId = timeMappingRepository.requestProjectId(timeMappingActivity.getProjectName());
                timeMappingRepository.createProjectActivity(projectId, userId,
                        timeMappingActivity.getActivityName(),
                        timeMappingActivity.getActivityHourlyRate());
            }
            return "Activity created";
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this activity already exists.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeMappingExceptions("Project ID not found.");
        } catch (EmptyResultDataAccessException e) {
            throw new TimeMappingExceptions("User ID not found.");
        }
    }

    public Boolean toggleActivity(TimeMappingLog timeMappingLog) {
        try {
            if (timeMappingLog.getProjectName() == null) {
                int activityId = timeMappingRepository.requestActivityId(timeMappingLog.getActivityName(),
                        timeMappingLog.getUserId());
                int tureValue = timeMappingRepository.getLogStatus(activityId, timeMappingLog.getUserId());
                if (tureValue > 0) {
                    timeMappingRepository.stopLog(activityId, timeMappingLog.getUserId());
                    return false;
                } else {
                    timeMappingRepository.startLog(activityId);
                    return true;
                }
            } else {
                int activityId = timeMappingRepository.requestProjectActivityId(timeMappingLog.getActivityName(),
                        timeMappingLog.getProjectName(), timeMappingLog.getUserId());
                int tureValue = timeMappingRepository.getLogStatus(activityId, timeMappingLog.getUserId());
                if (tureValue > 0) {
                    timeMappingRepository.stopLog(activityId, timeMappingLog.getUserId());
                    return false;
                } else {
                    timeMappingRepository.startLog(activityId);
                    return true;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new TimeMappingExceptions("Activity (or Project) name or user ID not found.");
        }
    }

    public List<DataSingleActivity> dataSingleActivity(String activityName,
                                                       int userId,
                                                       LocalDate startTime,
                                                       LocalDate stopTime) {
        List<DataSingleActivity> newList = timeMappingRepository.dataSingleActivity(activityName,
                userId, startTime, stopTime);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("Data not found.");
        } else {
            return timeMappingRepository.dataSingleActivity(activityName, userId, startTime, stopTime);
        }
    }

    public List<DataProject> dataProject(String projectName, int userId, LocalDate startTime, LocalDate stopTime) {
        List<DataProject> newList = timeMappingRepository.dataProject(projectName, userId, startTime, stopTime);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("Data not found.");
        } else {
            return timeMappingRepository.dataProject(projectName, userId, startTime, stopTime);
        }
    }
}