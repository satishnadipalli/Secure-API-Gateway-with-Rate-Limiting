package com.security.springsecurity.controllers;

import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import com.security.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registeruser")
    public ResponseEntity<UserModal> saveUser(@RequestBody UserwithPasswordModal userwithPasswordModal){
        UserModal savedUser = userService.registerUser(userwithPasswordModal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedUser);
    }

    @PostMapping("/loginuser")
    public ResponseEntity<UserModal> loginUser(@RequestBody LoginModalDTO loginModalDTO){
        UserModal  existingUser = userService.loginUser(loginModalDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(existingUser);
    }
}
