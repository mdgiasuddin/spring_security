package com.example.spring_security_demo.utils;

import com.example.spring_security_demo.common.Constants;
import com.example.spring_security_demo.datasource.BearerToken;
import com.example.spring_security_demo.repositories.BearerTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

@Component
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class JWTUtil {

    private final BearerTokenRepository bearerTokenRepository;

    private String SECRET_KEY = "Secret_Key_For_JWT_Token";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        BearerToken bearerToken = bearerTokenRepository.findByToken(token);

        if (bearerToken == null)
            return true;

        return LocalDateTime.now().isAfter(bearerToken.getTimeOut());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(Constants.TOKEN_TIMEOUT_MINUTE).toInstant()))
                .setHeaderParam("random", new Random().nextInt())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
