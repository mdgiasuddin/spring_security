package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.Player;
import com.example.spring_security_demo.datasource.Team;
import com.example.spring_security_demo.dtos.PlayerDTO;
import com.example.spring_security_demo.repositories.PlayerRepository;
import com.example.spring_security_demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TeamPlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamPlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public void addNewTeam() {
        List<Team> teamList = Arrays.asList(
                new Team("Barcelona", "Spain", "Morinho", "Joseph"),
                new Team("Real Madrid", "Spain", "Maradona", "Pele"),
                new Team("Abahoni", "Bangladesh", "Salauddin", "M Kamal"),
                new Team("Bayern Munich", "Argentina", "Perej", "Mussa"),
                new Team("Mohamedan", "Bangladesh", "Shuvo", "Saiful"),
                new Team("Chelsi", "Germany", "Kotinho", "Michael"),
                new Team("Liverpool", "France", "Ejaz", "Butler")
        );

//        teamRepository.saveAll(teamList);
    }

    public void addNewPlayer(List<PlayerDTO> playerDTOList) {
        List<Team> teamList = teamRepository.findAll();
        Random random = new Random();

        for (PlayerDTO playerDTO : playerDTOList) {
            Player player = new Player();
            player.setName(playerDTO.getName());
            player.setAge(playerDTO.getAge());
            player.setJerseyNo(playerDTO.getJerseyNo());
            player.setTeam(teamList.get(random.nextInt(teamList.size())));

            playerRepository.save(player);
        }
    }

    public List<PlayerDTO> getAllPlayer() {
        return playerRepository.getAllPlayer();
    }

    public List<PlayerDTO> getTeamPlayer(String teamName) {
        return playerRepository.getAllPlayer(teamName);
    }
}
