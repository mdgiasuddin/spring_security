package com.example.spring_security_demo.services;

import com.example.spring_security_demo.datasource.User;
import com.example.spring_security_demo.dtos.CreateUserDTO;
import com.example.spring_security_demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Object createNewUser(@Validated CreateUserDTO createUserDTO) throws Exception {
        Optional<User> user = userRepository.findByUsername(createUserDTO.getUsername());

        if (!user.equals(Optional.empty()))
            throw new Exception("User already exists with this username!");

        User newUser = new User();
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        newUser.setRoles(createUserDTO.getRoles());
        newUser.setActive(true);

        userRepository.save(newUser);

        return "User successfully created with username: " + createUserDTO.getUsername() + "!";
    }
}
