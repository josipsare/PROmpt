package com.example.PROmpt.service.impl;

import com.example.PROmpt.RecordNotFoundException;
import com.example.PROmpt.models.FinishedPrompt;
import com.example.PROmpt.models.User;
import com.example.PROmpt.models.UserPrompt;
import com.example.PROmpt.repository.FinishedPromptRepo;
import com.example.PROmpt.repository.UserRepo;
import com.example.PROmpt.service.FinishedPromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FinishedPromptServiceImpl implements FinishedPromptService {

    private final FinishedPromptRepo finishedPromptRepo;
    private final UserRepo userRepo;
    private final UserPromptServiceImpl userPromptService;

    @Override
    public Object getPrompts(Long userId) {

        if (userId==null){
            return finishedPromptRepo.findAll();
        }

        if (userRepo.findById(userId).isEmpty()){
            throw new RecordNotFoundException("User with provided ID doesn't exist");
        }

        User user = userRepo.findById(userId).get();

        List<FinishedPrompt> finishedPrompts = new ArrayList<>();
        List<UserPrompt> userPrompts = (List<UserPrompt>) userPromptService.getPrompts(userId);
        for(UserPrompt userPrompt : userPrompts){
            finishedPrompts.add(finishedPromptRepo.findByUserPrompt(userPrompt));
        }

        return finishedPrompts;
    }
}
