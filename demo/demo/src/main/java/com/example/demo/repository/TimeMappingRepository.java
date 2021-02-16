package com.example.demo.repository;

import com.example.demo.javaClasses.DataProject;
import com.example.demo.javaClasses.DataSingleActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class TimeMappingRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createUser(String firstName, String lastName, String email, String password) {
        String sql10 = "INSERT INTO user_data (first_name, last_name, email, password) " +
                "VALUES(:fnParam, :lnParam, :emailParam, :passwordParam)";
        HashMap<String, Object> paraMap10 = new HashMap<>();
        paraMap10.put("fnParam", firstName);
        paraMap10.put("lnParam", lastName);
        paraMap10.put("emailParam", email);
        paraMap10.put("passwordParam", password);
        jdbcTemplate.update(sql10, paraMap10);
    }

    public String requestPassword(String email) {
        String sql11 = "SELECT password FROM user_data WHERE email = :emailParam";
        HashMap<String, Object> paramap11 = new HashMap<>();
        paramap11.put("emailParam", email);
        return jdbcTemplate.queryForObject(sql11, paramap11, String.class);
    }

    public int getUserId(String email) {
        String sql19 = "SELECT user_id FROM user_data WHERE email = :emailParam";
        HashMap<String, Object> paramap19 = new HashMap<>();
        paramap19.put("emailParam", email);
        return jdbcTemplate.queryForObject(sql19, paramap19, Integer.class);
    }

    public int requestProjectId(String projectName) {
        String sql18 = "SELECT project_id FROM project WHERE project_name = :nameParam";
        HashMap<String, Object> paramap18 = new HashMap<>();
        paramap18.put("nameParam", projectName);
        return jdbcTemplate.queryForObject(sql18, paramap18, Integer.class);
    }

    public int requestActivityId(String activityName, int userId) {
        String sql14 = "SELECT activity_id FROM activity WHERE activity_name = :nameParam AND user_id = :idParam AND " +
                "project_id IS NULL";
        HashMap<String, Object> paramap14 = new HashMap<>();
        paramap14.put("nameParam", activityName);
        paramap14.put("idParam", userId);
        return jdbcTemplate.queryForObject(sql14, paramap14, Integer.class);
    }

    public int requestProjectActivityId(String activityName, String projectName, int userId) {
        String sql14 = "SELECT activity_id FROM activity WHERE activity_name = :nameParam AND user_id = :idParam AND " +
                "project_id IN (SELECT project_id FROM project WHERE project_name = :projParam)";
        HashMap<String, Object> paramap14 = new HashMap<>();
        paramap14.put("nameParam", activityName);
        paramap14.put("projParam", projectName);
        paramap14.put("idParam", userId);
        return jdbcTemplate.queryForObject(sql14, paramap14, Integer.class);
    }

    public void createProject(int userId, String projectName) {
        String sql = "INSERT INTO project (user_id, project_name) VALUES (:userIdParam, :projectNameParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userIdParam", userId);
        paramMap.put("projectNameParam", projectName);
        jdbcTemplate.update(sql, paramMap);
    }

    public void createProjectActivity(int projectId, int userId, String activityName, BigDecimal activityHourlyRate) {
        String sql = "INSERT INTO activity (project_id, user_id, activity_name, activity_hourly_rate) VALUES " +
                "(:projectIdParam, :userIdParam, :activityNameParam, :activityHourlyRateParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("projectIdParam", projectId);
        paramMap.put("userIdParam", userId);
        paramMap.put("activityNameParam", activityName);
        paramMap.put("activityHourlyRateParam", activityHourlyRate);
        jdbcTemplate.update(sql, paramMap);
    }

    public void createIndependentActivity(int userId, String activityName, BigDecimal activityHourlyRate) {
        String sql = "INSERT INTO activity (user_id, activity_name, activity_hourly_rate) VALUES " +
                "(:userIdParam, :activityNameParam, :activityHourlyRateParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userIdParam", userId);
        paramMap.put("activityNameParam", activityName);
        paramMap.put("activityHourlyRateParam", activityHourlyRate);
        jdbcTemplate.update(sql, paramMap);
    }

    public int getLogStatus(int activityId, int userId) {
        String sql = "SELECT count(*) FROM time_log WHERE log_status = true AND activity_id IN " +
                "(SELECT activity_id FROM activity WHERE user_id= :idParam AND activity_id = :accParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("accParam", activityId);
        paramMap.put("idParam", userId);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public void stopLog(int activityId, int userId) {
        String sql13 = "UPDATE time_log SET stop_time=now(), log_status=false, elapsed_time=(now()-time_log.start_time) " +
                "WHERE log_status = true AND activity_id IN (SELECT activity_id FROM activity " +
                "WHERE activity_id= :accParam AND user_id = :idParam)";
        HashMap<String, Object> paramap13 = new HashMap<>();
        paramap13.put("accParam", activityId);
        paramap13.put("idParam", userId);
        jdbcTemplate.update(sql13, paramap13);
    }

    public void startLog(int activityId) {
        String sql15 = "INSERT INTO time_log (activity_id, start_time, log_status) VALUES " +
                "(:idParam, :timeParam, :booleanParam)";
        HashMap<String, Object> paramap15 = new HashMap<>();
        paramap15.put("idParam", activityId);
        paramap15.put("timeParam", LocalDateTime.now());
        paramap15.put("booleanParam", true);
        jdbcTemplate.update(sql15, paramap15);
    }

    public List<DataSingleActivity> dataSingleActivity(String activityName, int userId, LocalDate startTime, LocalDate stopTime) {
        String sql16 = "SELECT activity.activity_name, round(cast(SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60 AS numeric),2) AS hours_spent, " +
                "round(cast((SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60)*activity.activity_hourly_rate AS numeric), 2) AS cost " +
                "FROM activity, time_log WHERE activity.activity_id = time_log.activity_id AND activity.activity_name= :nameParam AND user_id = :idParam AND " +
                "Date(time_log.start_time) >= :startParam AND Date(time_log.stop_time) <= :stopParam AND project_id IS NULL " +
                "GROUP BY activity_name, activity_hourly_rate";
        HashMap<String, Object> paraMap16 = new HashMap<>();
        paraMap16.put("nameParam", activityName);
        paraMap16.put("idParam", userId);
        paraMap16.put("startParam", startTime);
        paraMap16.put("stopParam", stopTime);
        List<DataSingleActivity> result = jdbcTemplate.query(sql16, paraMap16, new DataSingleActivityRowMapper());
        return result;
    }

    public List<DataProject> dataProject(String projectName, int userId, LocalDate startTime, LocalDate stopTime) {
        String sql19 = "SELECT project.project_name, user_data.last_name, activity.activity_name, " +
                "round(cast(SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60 AS numeric),2) AS hours_spent, " +
                "round(cast((SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60)*activity.activity_hourly_rate AS numeric), 2) AS cost " +
                "FROM project, activity, user_data, time_log WHERE time_log.activity_id = activity.activity_id AND " +
                "activity.project_id=project.project_id AND activity.user_id=user_data.user_id AND " +
                "project.project_name= :nameParam AND project.user_id= :idParam AND Date(time_log.start_time) >= :startParam AND " +
                "Date(time_log.stop_time) <= :stopParam GROUP BY project_name, last_name, activity_name, activity_hourly_rate";
        HashMap<String, Object> paraMap19 = new HashMap<>();
        paraMap19.put("nameParam", projectName);
        paraMap19.put("idParam", userId);
        paraMap19.put("startParam", startTime);
        paraMap19.put("stopParam", stopTime);
        List<DataProject> result = jdbcTemplate.query(sql19, paraMap19, new DataProjectRowMapper());
        return result;

    }

    private class DataSingleActivityRowMapper implements RowMapper<DataSingleActivity> {
        @Override
        public DataSingleActivity mapRow(ResultSet resultSet, int i) throws SQLException {
            DataSingleActivity dataSingleActivity = new DataSingleActivity();
            dataSingleActivity.setActivityName(resultSet.getString("activity_name"));
            dataSingleActivity.setHours(resultSet.getDouble("hours_spent"));
            dataSingleActivity.setCost(resultSet.getBigDecimal("cost"));
            return dataSingleActivity;
        }
    }

    private class DataProjectRowMapper implements RowMapper<DataProject> {
        @Override
        public DataProject mapRow(ResultSet resultSet, int i) throws SQLException {
            DataProject dataProject = new DataProject();
            dataProject.setProjectName(resultSet.getString("project_name"));
            dataProject.setLastName(resultSet.getString("last_name"));
            dataProject.setActivityName(resultSet.getString("activity_name"));
            dataProject.setHours(resultSet.getDouble("hours_spent"));
            dataProject.setCost(resultSet.getBigDecimal("cost"));
            return dataProject;
        }
    }
}
