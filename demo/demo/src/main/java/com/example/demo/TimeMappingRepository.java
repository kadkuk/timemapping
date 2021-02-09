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

    public String requestPassword(String email) {
        String sql11 = "SELECT password FROM user_data WHERE email = :emailParam";
        HashMap<String, Object> paramap11 = new HashMap<>();
        paramap11.put("emailParam", email);
        return jdbcTemplate.queryForObject(sql11, paramap11, String.class);
    }


    public void start_log (int logID) {

    }
}
