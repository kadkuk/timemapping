package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Repository
public class TimeMappingRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createUser(String firstName, String lastName, String email, String password) {
        String sql10 = "INSERT INTO user_data (first_name, last_name, email, password) VALUES(:fnParam, :lnParam, :emailParam, :passwordParam)";
        HashMap<String, Object> paraMap10 = new HashMap<>();
        paraMap10.put("fnParam", firstName);
        paraMap10.put("lnParam", lastName);
        paraMap10.put("emailParam", email);
        paraMap10.put("passwordParam", password);
        jdbcTemplate.update(sql10, paraMap10);
    }

    public void createProject(int userId, String projectName){
        String sql = "INSERT INTO project (user_id, project_name) VALUES (:userIdParam, :projectNameParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userIdParam", userId);
        paramMap.put("projectNameParam", projectName);
        jdbcTemplate.update(sql, paramMap);
    }

    public void createActivity(int projectId, int userId, String activityName, int activityHourlyRate) {
        String sql = "INSERT INTO activity (project_id, user_id, activity_name, activity_hourly_rate) VALUES (:projectIdParam, :userIdParam, :activityNameParam, :activityHourlyRateParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("projectIdParam", projectId);
        paramMap.put("userIdParam", userId);
        paramMap.put("activityNameParam", activityName);
        paramMap.put("activityHourlyRateParam", activityHourlyRate);
        jdbcTemplate.update(sql, paramMap);

    }


    public String requestPassword(String email) {
        String sql11 = "SELECT password FROM user_data WHERE email = :emailParam";
        HashMap<String, Object> paramap11 = new HashMap<>();
        paramap11.put("emailParam", email);
        return jdbcTemplate.queryForObject(sql11, paramap11, String.class);
    }


    public void startLog (int logID) {
        String sql = "UPDATE time_log SET start_time = current_timestamp WHERE log_id = :logIdParam";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("logIdParam", logID);
        jdbcTemplate.update(sql, paramMap);
    }

    public void stopLog (int logID) {
        String sql = "UPDATE time_log SET stop_time = current_timestamp WHERE log_id = :logIdParam";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("logIdParam", logID);
        jdbcTemplate.update(sql, paramMap);
    }

    public boolean getLogStatus (int logID ) {
        String sql = "SELECT log_status FROM time_log WHERE log_id = :logIdParam";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("LogIdParam", logID);
        return jdbcTemplate.queryForObject(sql, paramMap, Boolean.class);
    }

    public void setLogStatusTrue(int logId) {
        String sql = "UPDATE time_log SET log_status = :logStatusParam WHERE log_id = :logIdParam";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("logIdParam", logId);
        paramMap.put("logStatusParam", true);
        jdbcTemplate.update(sql, paramMap);
    }



}
