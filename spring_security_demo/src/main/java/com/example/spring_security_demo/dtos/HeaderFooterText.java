package com.example.spring_security_demo.dtos;

import lombok.Data;

@Data
public class HeaderFooterText {
    private String text;
    private Text2DPoint point;

    public HeaderFooterText(String text, Text2DPoint point) {
        this.text = text;
        this.point = point;
    }
}
