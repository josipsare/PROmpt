package com.example.PROmpt.service;


import com.example.PROmpt.DTO.UserPromptDTO;
import com.example.PROmpt.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserPromptService {
    Object save(UserPromptDTO userPrompt,User currentUser);

    Object enhance(UserPromptDTO userPromptDTO, String llm, User currentUser) ;

    Object getPrompts(Long userId);
}
