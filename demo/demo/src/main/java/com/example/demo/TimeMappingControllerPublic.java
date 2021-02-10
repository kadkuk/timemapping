package com.example.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class TimeMappingControllerPublic {

    @Autowired
    private TimeMappingService timeMappingService;

    @Autowired
    private TimeMappingRepository timeMappingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //http://localhost:8080/public/createuser
    @PostMapping ("/public/createuser")
    public String createUser(@RequestBody TimeMappingUser user) {
        return timeMappingService.createUser(user);
    }

    //http://localhost:8080/public/login
    @PostMapping("/public/login")
    public String login(@RequestBody Login login) {
        if (validate(login)) {
            Date now = new Date();
            Date expired = new Date(now.getTime() + 1000 * 60 * 60); //token valid 1h
            JwtBuilder builder = Jwts.builder()
                    .setExpiration(expired)
                    .setIssuedAt(new Date())
                    .setIssuer("vali-it")
                    .signWith(SignatureAlgorithm.HS256, "c2VjcmV0")
                    .claim("userName", login.getEmail());
            return builder.compact();
        } else {
            throw new TimeMappingExceptions("Username or password not found.");
        }
    }

    public Boolean validate(Login login) {
        String encodedPassword = timeMappingRepository.requestPassword(login.getEmail());
        return passwordEncoder.matches(login.getPassword(), encodedPassword);
    }

}