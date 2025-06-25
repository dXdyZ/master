package com.example.integrationteststudi.repository;

import com.example.integrationteststudi.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl  {
    @Cacheable(value = "user", key = "{#id}")
    public User findById(int id) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(new User((long) i, "name: " + i));
        }
        return users.get(id);
    }
}
