package com.example.spring_security_demo.datasource;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bearer_token")
public class BearerToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(name = "time_out", columnDefinition = "TIMESTAMP")
    private LocalDateTime timeOut;


}
