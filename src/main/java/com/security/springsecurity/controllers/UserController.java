package com.security.springsecurity.controllers;

import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import com.security.springsecurity.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/registeruser")
    public ResponseEntity<?> saveUser(@RequestBody UserwithPasswordModal userwithPasswordModal){
        logger.info("Information received");
        Map<String,Object> savedUser = userService.registerUser(userwithPasswordModal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedUser);
    }

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello world";
    }


    @PostMapping("/loginuser")
    public ResponseEntity<UserModal> loginUser(@RequestBody LoginModalDTO loginModalDTO){
        UserModal  existingUser = userService.loginUser(loginModalDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(existingUser);
    }
}
