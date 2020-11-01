package com.example.spring_security_demo.resources;

import com.example.spring_security_demo.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenService {

    private final JWTUtil jwtUtil;

    protected String getUsername(HttpServletRequest httpServletRequest) {

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username;
        String jwtToken;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);

            System.out.println("token: " + jwtToken);
            username = jwtUtil.extractUsername(jwtToken);

            return username;

        }

        return null;
    }
}
