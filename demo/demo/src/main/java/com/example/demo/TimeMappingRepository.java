package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
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

    public void stopLog(String activityName, int userID) {
        String sql13 = "UPDATE time_log SET stop_time=now(), log_status=false, elapsed_time=(now()-time_log.start_time) WHERE log_status = true AND activity_id IN (SELECT activity_id FROM activity WHERE activity_name= :nameParam AND user_id = :idParam)";
        HashMap <String, Object> paramap13 = new HashMap<>();
        paramap13.put("nameParam", activityName);
        paramap13.put("idParam", userID);
        jdbcTemplate.update(sql13, paramap13);
    }

    public int requestActivityId(String activityName, int userID) {
        String sql14 = "SELECT activity_id FROM activity WHERE activity_name = :nameParam AND user_id = :idParam";
        HashMap <String, Object> paramap14 = new HashMap<>();
        paramap14.put("nameParam", activityName);
        paramap14.put("idParam", userID);
        return jdbcTemplate.queryForObject(sql14, paramap14, Integer.class);
    }

    public void startLog(int activityId) {
        String sql15 = "INSERT INTO time_log (activity_id, start_time, log_status) VALUES (:idParam, :timeParam, :booleanParam)";
        HashMap <String, Object> paramap15 = new HashMap<>();
        paramap15.put("idParam", activityId);
        paramap15.put("timeParam", LocalDateTime.now());
        paramap15.put("booleanParam", true);
        jdbcTemplate.update(sql15, paramap15);
    }

    public int getLogStatus (String activityName, int userID) {
        String sql = "SELECT count(*) FROM time_log WHERE activity_id IN (SELECT activity_id FROM activity WHERE user_id= :idParam AND log_status=true AND activity_name = :nameParam)";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("nameParam", activityName);
        paramMap.put("idParam", userID);
        return jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    public List<ActivityHoursCosts> activityHourlyCosts (String activityName, int userID) {
        String sql16 = "SELECT activity.activity_name, SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60 AS hours, (SUM(EXTRACT(epoch from (time_log.elapsed_time)))/60/60)*activity.activity_hourly_rate AS cost FROM activity, time_log WHERE activity.activity_id = time_log.activity_id AND activity.activity_name= :nameParam AND activity.user_id = :idParam GROUP BY activity_name, activity_hourly_rate;";
        HashMap<String, Object> paraMap16 = new HashMap<>();
        paraMap16.put("nameParam", activityName);
        paraMap16.put("idParam", userID);
        List<ActivityHoursCosts> result = jdbcTemplate.query(sql16, paraMap16, new ActivityHoursCostsRowMapper());
        return result;
    }



    private class ActivityHoursCostsRowMapper implements RowMapper<ActivityHoursCosts> {
        @Override
        public ActivityHoursCosts mapRow(ResultSet resultSet, int i) throws SQLException {
            ActivityHoursCosts activityHoursCosts = new ActivityHoursCosts();
            activityHoursCosts.setActivityName(resultSet.getString("activity_name"));
            activityHoursCosts.setHours(resultSet.getDouble("hours"));
            activityHoursCosts.setCost(resultSet.getBigDecimal("cost"));
            return activityHoursCosts;
        }
    }


}
