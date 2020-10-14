package com.example.spring_security_demo.dtos;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private String roles;
}
