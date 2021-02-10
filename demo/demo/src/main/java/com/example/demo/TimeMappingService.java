package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TimeMappingService {

    @Autowired
    private TimeMappingRepository timeMappingRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(TimeMappingUser user) {
        try {
            timeMappingRepository.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), passwordEncoder.encode(user.getPassword()));
            return "User created!";
        } catch (DuplicateKeyException e) {
            throw new TimeMappingExceptions("User with this email already exists.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeMappingExceptions("Email cannot be empty");
        } catch (IllegalArgumentException e) {
            throw new TimeMappingExceptions("Password cannot be empty");
        }
    }

    public void createProject(TimeMappingProject timeMappingProject) {
       timeMappingRepository.createProject(timeMappingProject.getUserId(),
               timeMappingProject.getProjectName());
    }

    public void createActivity(TimeMappingActivity timeMappingActivity) {
        timeMappingRepository.createActivity(timeMappingActivity.getProjectId(),
                timeMappingActivity.getUserId(),
                timeMappingActivity.getActivityName(),
                timeMappingActivity.getActivityHourlyRate());
    }

    public void startLog (TimeMappingLog timeMappingLog) {
        timeMappingRepository.startLog(timeMappingLog.getLogId());
    }

    public void stopLog (TimeMappingLog timeMappingLog) {
        timeMappingRepository.stopLog(timeMappingLog.getLogId());
    }
}