package com.example.spring_security_demo.controllers;

import com.example.spring_security_demo.dtos.CreateUserDTO;
import com.example.spring_security_demo.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/admin/create-new-user")
    public Object createNewUser(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        return userManagementService.createNewUser(createUserDTO);
    }

    @PostMapping("/first")
    public Object createAdmin(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        return userManagementService.createNewUser(createUserDTO);
    }
}
