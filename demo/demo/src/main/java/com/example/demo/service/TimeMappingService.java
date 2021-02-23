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
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;


@Service
public class TimeMappingService {

    @Autowired
    private TimeMappingRepository timeMappingRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(TimeMappingUser user) {
        try {
            if (user.getEmail().equals("") && user.getPassword().equals("") || user.getEmail() == null
                    || user.getPassword() == null) {
                throw new TimeMappingExceptions("Email and password cannot be empty");
            } else {
                timeMappingRepository.createUser(user.getFirstName(), user.getLastName(), user.getEmail().toLowerCase(Locale.ROOT),
                        passwordEncoder.encode(user.getPassword()));
                return "User created!";
            }
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this email already exists.");
        } catch (EmptyResultDataAccessException e){
            throw new TimeMappingExceptions("Email and password cannot be empty.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeMappingExceptions("Email cannot be empty");
        } catch (IllegalArgumentException e) {
            throw new TimeMappingExceptions("Password cannot be empty");
        }
    }

    public String createProject(TimeMappingProject timeMappingProject, Integer id) {
        try {
            if (timeMappingProject.getProjectName().equals("")) {
                throw new TimeMappingExceptions("Please insert project name.");
            } else {
                timeMappingRepository.createProject(id, timeMappingProject.getProjectName().toLowerCase(Locale.ROOT));
                return "Project created.";
            }
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this project already exists.");
        } catch (EmptyResultDataAccessException e) {
            throw new TimeMappingExceptions("User does not exist. Please create your user and login.");
        }
    }

    public String createActivity(TimeMappingActivity timeMappingActivity, Integer id) {
        try {
            if (timeMappingActivity.getProjectName() == null || timeMappingActivity.getProjectName().equals("")) {
                if (timeMappingActivity.getActivityName().equals("")) {
                    throw new TimeMappingExceptions("Please insert activity name.");
                } else {
                    timeMappingRepository.createIndependentActivity(id,
                            timeMappingActivity.getActivityName().toLowerCase(Locale.ROOT),
                            timeMappingActivity.getActivityHourlyRate());
                }
            } else {
                if (timeMappingActivity.getActivityName().equals("")) {
                    throw new TimeMappingExceptions("Please insert activity name.");
                } else {
                    int projectId = timeMappingRepository.requestProjectId(timeMappingActivity.getProjectName().toLowerCase(Locale.ROOT));
                    timeMappingRepository.createProjectActivity(projectId, id,
                            timeMappingActivity.getActivityName().toLowerCase(Locale.ROOT),
                            timeMappingActivity.getActivityHourlyRate());
                }
            }
            return "Activity created";
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this activity already exists.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeMappingExceptions("Project ID not found.");
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new TimeMappingExceptions("User not found. Please create your user and login.");
        }
    }

    public Boolean toggleActivity(TimeMappingLog timeMappingLog, Integer id) {
        try {
            if (timeMappingLog.getProjectName() == null || timeMappingLog.getProjectName().equals("")) {
                int activityId = timeMappingRepository.requestActivityId(timeMappingLog.getActivityName().toLowerCase(Locale.ROOT),
                        id);
                int tureValue = timeMappingRepository.getLogStatus(activityId, id);
                if (tureValue > 0) {
                    timeMappingRepository.stopLog(activityId, id);
                    return false;
                } else {
                    timeMappingRepository.startLog(activityId);
                    return true;
                }
            } else {
                int activityId = timeMappingRepository.requestProjectActivityId(timeMappingLog.getActivityName().toLowerCase(Locale.ROOT),
                        timeMappingLog.getProjectName().toLowerCase(Locale.ROOT), id);
                int tureValue = timeMappingRepository.getLogStatus(activityId, id);
                if (tureValue > 0) {
                    timeMappingRepository.stopLog(activityId, id);
                    return false;
                } else {
                    timeMappingRepository.startLog(activityId);
                    return true;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            throw new TimeMappingExceptions("Activity or project not found.");
        }
    }

    public List<DataSingleActivity> dataSingleActivity(int activityId,
                                                       int userId,
                                                       LocalDate startTime,
                                                       LocalDate stopTime) {
        List<DataSingleActivity> newList = timeMappingRepository.dataSingleActivity(activityId,
                userId, startTime, stopTime);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("Data not found.");
        } else {
            return timeMappingRepository.dataSingleActivity(activityId, userId, startTime, stopTime);
        }
    }

    public List<DataProject> dataProject(int projectId, int userId, LocalDate startTime, LocalDate stopTime) {
        List<DataProject> newList = timeMappingRepository.dataProject(projectId, userId, startTime, stopTime);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("Data not found.");
        } else {
            return timeMappingRepository.dataProject(projectId, userId, startTime, stopTime);
        }
    }

    public List<ListActivities> listActivities(int userId) {
        List<ListActivities> newList = timeMappingRepository.listActivities(userId);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("No activities found.");
        } else {
            return timeMappingRepository.listActivities(userId);
        }
    }

    public List<ListProject> listProject(int userId) {
        List<ListProject> newList = timeMappingRepository.listProject(userId);
        if (newList.isEmpty()) {
            throw new TimeMappingExceptions("No projects found.");
        } else {
            return timeMappingRepository.listProject(userId);
        }
    }
}