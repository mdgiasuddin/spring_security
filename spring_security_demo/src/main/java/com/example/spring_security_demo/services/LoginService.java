package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.BearerToken;
import com.example.spring_security_demo.repositories.BearerTokenRepository;
import com.example.spring_security_demo.resources.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginService {
    private final BearerTokenRepository bearerTokenRepository;
    private final TokenService tokenService;
    private final HttpServletRequest httpServletRequest;


    public String logMeOut() {
        String token = tokenService.getBearerTokenString(httpServletRequest);

        BearerToken bearerToken = bearerTokenRepository.findByToken(token);

        if (bearerToken != null) {
            bearerToken.setTimeOut(LocalDateTime.now().minusSeconds(10));
            bearerTokenRepository.save(bearerToken);
        }

        return "Successfully Logout";
    }
}
