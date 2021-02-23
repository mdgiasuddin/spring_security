package com.example.spring_security_demo.controllers;


import com.example.spring_security_demo.services.JsonParsingService;
import com.example.spring_security_demo.services.MiscellaneousService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/miscellaneous")
public class MiscellaneousController {

    private final MiscellaneousService miscellaneousService;
    private final JsonParsingService jsonParsingService;

    @PostMapping("/number-to-word")
    public String numberToWord(@RequestBody Map map) {
        return miscellaneousService.numberToWord(String.valueOf(map.get("number")));
    }

    @GetMapping("/parse-json")
    public void parseJson() {
        jsonParsingService.parseJson();
    }

    @GetMapping("/map-test")
    public Object mapTest() {
        return miscellaneousService.mapTest();
    }
}
