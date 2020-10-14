package com.example.spring_security_demo.datasource;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private boolean active;
    private String roles;

}
