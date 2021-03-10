package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.common.ConstantsClass;
import com.example.spring_security_demo.datasource.BearerToken;
import com.example.spring_security_demo.datasource.User;
import com.example.spring_security_demo.models.AuthenticationRequest;
import com.example.spring_security_demo.models.AuthenticationResponse;
import com.example.spring_security_demo.repositories.BearerTokenRepository;
import com.example.spring_security_demo.repositories.UserRepository;
import com.example.spring_security_demo.resources.MyUserDetailsService;
import com.example.spring_security_demo.services.LoginService;
import com.example.spring_security_demo.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JWTUtil jwtUtil;
    private final HttpServletRequest httpServletRequest;
    private final BearerTokenRepository bearerTokenRepository;
    private final LoginService loginService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        User user = new User();
        user.setUsername("xyz");
        user.setPassword("xyz");
        user.setActive(true);
        user.setRoles("ROLE_ADMIN");
        userRepository.save(user);

        return ("<h1>Welcome</h1>");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        BearerToken bearerToken = new BearerToken();
        bearerToken.setToken(jwtToken);
        bearerToken.setUsername(authenticationRequest.getUsername());
        bearerToken.setTimeOut(LocalDateTime.now().plusMinutes(ConstantsClass.TOKEN_TIMEOUT_MINUTE));

        bearerTokenRepository.save(bearerToken);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @GetMapping("/user")
    public String user() {
        System.out.println("Inside Controller Hello User");
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/logout-me")
    public String logMeOut() {
        System.out.println("Inside Controller Hello User");
        return loginService.logMeOut();
    }

    @PostMapping("/user/insert-database")
    public String insertDatabase() {
        return ("<h1>Insert Database</h1>");
    }



    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }
}
