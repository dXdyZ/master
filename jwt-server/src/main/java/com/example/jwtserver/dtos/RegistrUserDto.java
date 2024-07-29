package com.example.jwtserver.dtos;

import com.example.jwtserver.enity.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
public class RegistrUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

}
