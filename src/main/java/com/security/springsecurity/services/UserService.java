package com.security.springsecurity.services;


import com.security.springsecurity.entities.User;
import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import io.jsonwebtoken.io.IOException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends UserDetailsService {
    User loadUserByUsername(String username) throws IOException;

    Map<String,Object> registerUser(UserwithPasswordModal userwithPasswordModal);

    UserModal loginUser(LoginModalDTO loginModalDTO);
//    UserModal registerUser(UserwithPasswordModal userwithPasswordModal);
}
