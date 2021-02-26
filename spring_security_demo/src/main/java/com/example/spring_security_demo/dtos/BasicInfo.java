package com.example.spring_security_demo.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class BasicInfo {
    Map<String, Object> map;
    private String name;
    private String address;

}
