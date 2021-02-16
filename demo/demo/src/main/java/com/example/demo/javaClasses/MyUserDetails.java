package com.example.demo.javaClasses;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails extends User {
    private final Integer id;

    public Integer getId() {
        return id;
    }

    public MyUserDetails(String username, Integer id, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id=id;

    }
}
