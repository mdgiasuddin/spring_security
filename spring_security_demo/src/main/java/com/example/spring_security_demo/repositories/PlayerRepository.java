package com.example.spring_security_demo.repositories;

import com.example.spring_security_demo.datasource.Player;
import com.example.spring_security_demo.dtos.PlayerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select new com.example.spring_security_demo.dtos.PlayerDTO(p) from Player p")
    List<PlayerDTO> getAllPlayer();

    @Query("select new com.example.spring_security_demo.dtos.PlayerDTO(p) from Player p where p.team.name = :teamName")
    List<PlayerDTO> getAllPlayer(@Param("teamName") String teamName);

}
