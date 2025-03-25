package com.security.springsecurity.services;

import com.security.springsecurity.Exceptions.ResourceNotFoundException;
import com.security.springsecurity.entities.User;
import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import com.security.springsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserModal registerUser(UserwithPasswordModal userwithPasswordModal) {
        User user = new User();
        user.setEmail(userwithPasswordModal.getEmail());
        user.setUserName(userwithPasswordModal.getUserName());
        user.setPassword(passwordEncoder.encode(userwithPasswordModal.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserModal(savedUser.getUserName(),savedUser.getEmail());
    }

    @Override
    public UserModal loginUser(LoginModalDTO loginModalDTO) {
        System.out.println(loginModalDTO.getPassword()+"-"+loginModalDTO.getUserNameOrEmail());
        if(loginModalDTO.getPassword() == null || loginModalDTO.getPassword().length() <= 0 || loginModalDTO.getUserNameOrEmail() == null){
            throw new ResourceNotFoundException("Please provide the necessary details");
        }

        User existingUser = userRepository.findByUserNameOrEmail(loginModalDTO.getUserNameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginModalDTO.getUserNameOrEmail()).orElseThrow(
                        () -> new ResourceNotFoundException("The requested user not exist. Please Login")
                ));

        if(!passwordEncoder.matches(existingUser.getPassword(), loginModalDTO.getPassword())){
            throw new ResourceNotFoundException("Password MissMatched");
        }

        return new UserModal(existingUser.getUserName(),existingUser.getEmail());

    }


}
