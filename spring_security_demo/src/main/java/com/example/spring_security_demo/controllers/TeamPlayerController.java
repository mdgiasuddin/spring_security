package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.dtos.PlayerDTO;
import com.example.spring_security_demo.services.TeamPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/play")
public class TeamPlayerController {

    @Autowired
    private TeamPlayerService teamPlayerService;

    @PostMapping("/team/new")
    public void addNewTeam() {
        teamPlayerService.addNewTeam();
    }

    @PostMapping("/player/new")
    public void addNewPlayer(@RequestBody @Validated List<PlayerDTO> playerDTOList) {
        teamPlayerService.addNewPlayer(playerDTOList);
    }

    @GetMapping("/player/all")
    public List<PlayerDTO> getAllPlayer() {
        return teamPlayerService.getAllPlayer();
    }


}
