package com.example.spring_security_demo.resources;

import com.example.spring_security_demo.datasource.User;
import com.example.spring_security_demo.models.AuthenticationRequest;
import com.example.spring_security_demo.models.AuthenticationResponse;
import com.example.spring_security_demo.repositories.UserRepository;
import com.example.spring_security_demo.utils.JWTUtil;
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

@RestController
public class HomeResource {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/")
    public String home() {
/*        User user = new User();
        user.setUsername("xyz");
        user.setPassword("xyz");
        user.setActive(true);
        user.setRoles("ROLE_ADMIN");
        userRepository.save(user);*/

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

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @GetMapping("/user")
    public String user() {
        System.out.println("Inside Controller Hello User");
        return ("<h1>Welcome User</h1>");
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
