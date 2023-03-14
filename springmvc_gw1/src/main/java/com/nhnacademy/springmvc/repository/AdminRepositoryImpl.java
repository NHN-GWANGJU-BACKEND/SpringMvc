package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Admin;

import java.util.Optional;

public class AdminRepositoryImpl implements AdminRepository {
    private final Admin admin = new Admin("admin", "12345");

    @Override
    public boolean exists(String id) {
        return admin.getId().equals(id);
    }

    @Override
    public Admin getAdmin(String id) {
        return exists(id) ? admin : null;
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getAdmin(id))
                .map(admin -> admin.getPassword().equals(password))
                .orElse(false);
    }
}
