package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Admin;

public interface AdminRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    Admin getAdmin(String id);
}
