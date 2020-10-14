package com.example.spring_security_demo.models;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private final String jwtToken;

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
