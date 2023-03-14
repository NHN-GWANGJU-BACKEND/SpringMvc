package com.nhnacademy.springmvc.domain;

import lombok.Value;

@Value
public class Admin {
    private final String id;
    private final String password;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
