package com.example.spring_security_demo.filters;

import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.datasource.BearerToken;
import com.example.spring_security_demo.repositories.BearerTokenRepository;
import com.example.spring_security_demo.resources.MyUserDetailsService;
import com.example.spring_security_demo.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final BearerTokenRepository bearerTokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);

            System.out.println("token: " + jwtToken);
            username = jwtUtil.extractUsername(jwtToken);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                BearerToken bearerToken = bearerTokenRepository.findByToken(jwtToken);
                bearerToken.setTimeOut(LocalDateTime.now().plusMinutes(ConstantsClass.TOKEN_TIMEOUT_MINUTE));
                bearerTokenRepository.save(bearerToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

}
