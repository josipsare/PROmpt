package com.example.PROmpt.auth;


import com.example.PROmpt.RecordNotFoundException;
import com.example.PROmpt.configuration.JwtService;
import com.example.PROmpt.models.Role;
import com.example.PROmpt.models.User;
import com.example.PROmpt.repository.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> userOptinal = userRepo.findByEmail(request.getEmail());
        if (userOptinal.isPresent()){
            throw new RecordNotFoundException("Email is already taken");
        }

        var user= User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException exc) {
            throw new RecordNotFoundException("Email or password is incorrect.");

        }
        //user is authenticated
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(()->new RecordNotFoundException("Wrong email or password"));
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
