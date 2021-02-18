package com.example.demo.javaClasses;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserFactory.class)
public @interface WithMockCustomUser {

    String username() default "toots@gmail.com";

    String name() default "Rob Winch";

    String id() default "18";
}