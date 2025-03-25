package com.security.springsecurity.services;


import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModal registerUser(UserwithPasswordModal userwithPasswordModal);

    UserModal loginUser(LoginModalDTO loginModalDTO);
//    UserModal registerUser(UserwithPasswordModal userwithPasswordModal);
}
