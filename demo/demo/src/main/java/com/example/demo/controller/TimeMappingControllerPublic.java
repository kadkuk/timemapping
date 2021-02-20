package com.example.demo.controller;

import com.example.demo.javaClasses.TimeMappingUser;
import com.example.demo.repository.TimeMappingRepository;
import com.example.demo.securityAndErrorHandling.Login;
import com.example.demo.securityAndErrorHandling.TimeMappingExceptions;
import com.example.demo.service.TimeMappingService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Locale;

@RestController
public class TimeMappingControllerPublic {

    @Autowired
    private TimeMappingService timeMappingService;

    @Autowired
    private TimeMappingRepository timeMappingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //http://localhost:8080/public/createuser
    @PostMapping("/public/createuser")
    public String createUser(@RequestBody TimeMappingUser user) {
        return timeMappingService.createUser(user);
    }

    //http://localhost:8080/public/login
    @PostMapping("/public/login")
    public String login(@RequestBody Login login) {
        if (login.getEmail().equals("") && login.getPassword().equals("")) {
            throw new TimeMappingExceptions("Please enter your login name and password.");
        } else {
            if (validate(login)) {
                Date now = new Date();
                Date expired = new Date(now.getTime() + 1000 * 60 * 60 * 24); //token valid 24h
                JwtBuilder builder = Jwts.builder()
                        .setExpiration(expired)
                        .setIssuedAt(new Date())
                        .setIssuer("vali-it")
                        .signWith(SignatureAlgorithm.HS256, "c2VjcmV0")
                        .claim("userName", login.getEmail().toLowerCase(Locale.ROOT))
                        .claim("userId", timeMappingRepository.getUserId(login.getEmail().toLowerCase(Locale.ROOT)));
                return builder.compact();
            } else {
                throw new TimeMappingExceptions("Username or password not found.");
            }
        }
    }

    public Boolean validate(Login login) {
        String encodedPassword = timeMappingRepository.requestPassword(login.getEmail().toLowerCase(Locale.ROOT));
        return passwordEncoder.matches(login.getPassword(), encodedPassword);
    }

}