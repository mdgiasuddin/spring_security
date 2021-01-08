package com.example.spring_security_demo.controllers;


import com.example.spring_security_demo.services.MiscellaneousService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/miscellaneous")
public class MiscellaneousController {

    private final MiscellaneousService miscellaneousService;

    @PostMapping("/number-to-word")
    public String numberToWord(@RequestBody Map map) {
        return miscellaneousService.numberToWord(String.valueOf(map.get("number")));
    }
}
