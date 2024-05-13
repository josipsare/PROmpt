package com.example.PROmpt.service.impl;

import com.example.PROmpt.RecordNotFoundException;
import com.example.PROmpt.models.User;
import com.example.PROmpt.repository.UserRepo;
import com.example.PROmpt.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Object getUsers(Long userId) {
        if (userId==null){
            return userRepo.findAll();
        }else{
            Optional<User> userMyb = userRepo.findById(userId);
            if(userMyb.isPresent()){
                return userMyb.get();
            }else{
                throw new RecordNotFoundException("User with provided ID doesn't exist");
            }
        }

    }
}
