package com.example.integrationteststudi.repository;

import com.example.integrationteststudi.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int id);
}
