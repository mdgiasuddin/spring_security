package com.example.spring_security_demo.repositories;

import com.example.spring_security_demo.datasource.BearerToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BearerTokenRepository extends JpaRepository<BearerToken, String> {

    BearerToken findByToken(String token);
}
