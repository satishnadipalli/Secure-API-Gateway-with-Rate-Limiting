package com.security.springsecurity.services;

import com.security.springsecurity.Exceptions.ResourceNotFoundException;
import com.security.springsecurity.config.JwtUtil;
import com.security.springsecurity.entities.User;
import com.security.springsecurity.models.LoginModalDTO;
import com.security.springsecurity.models.UserwithPasswordModal;
import com.security.springsecurity.models.UserModal;
import com.security.springsecurity.repositories.UserRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public User loadUserByUsername(String username) throws IOException {
        return userRepository.findByUserName(username).orElseThrow(
                () -> new ResourceNotFoundException("The requested Username is not found, please try another")
        );
    }

    @Override
    public Map<String,Object> registerUser(UserwithPasswordModal userwithPasswordModal) {
        User user = new User();

        System.out.println(userwithPasswordModal.toString());
        user.setEmail(userwithPasswordModal.getEmail());
        user.setUserName(userwithPasswordModal.getUserName());
        user.setPassword(passwordEncoder.encode(userwithPasswordModal.getPassword()));
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUserName());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", savedUser.getEmail());
        response.put("userName", savedUser.getUserName());
        return response;
    }

    @Override
    public UserModal loginUser(LoginModalDTO loginModalDTO) {
        if (loginModalDTO.getPassword() == null || loginModalDTO.getPassword().isEmpty() || loginModalDTO.getUserNameOrEmail() == null) {
            throw new ResourceNotFoundException("Please provide the necessary details");
        }

        User existingUser = userRepository.findByUserNameOrEmail(loginModalDTO.getUserNameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginModalDTO.getUserNameOrEmail()).orElseThrow(
                        () -> new ResourceNotFoundException("The requested user does not exist. Please Login")
                ));

        if (!passwordEncoder.matches(existingUser.getPassword(), loginModalDTO.getPassword())) {
            throw new ResourceNotFoundException("Password Mismatched");
        }

        return new UserModal(existingUser.getUsername(), existingUser.getEmail());
    }
}
