package com.example.spring_security_demo.repositories;

import com.example.spring_security_demo.datasource.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
