package com.example.spring_security_demo.dtos;

import com.example.spring_security_demo.datasource.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    private Long id;
    private String name;
    private Long age;
    private Long jerseyNo;
    private Long teamId;
    private String teamName;

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.age = player.getAge();
        this.jerseyNo = player.getJerseyNo();
        this.teamId = player.getTeam().getId();
        this.teamName = player.getTeam().getName();
    }
}
