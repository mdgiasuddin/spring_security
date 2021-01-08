package com.example.spring_security_demo.dtos;

import lombok.Data;

@Data
public class Text2DPoint {
    private float x;
    private float y;
    private float rotation;

    public Text2DPoint(float x, float y) {
        this.x = x;
        this.y = y;
        this.rotation = 0f;
    }

    public Text2DPoint(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
}
