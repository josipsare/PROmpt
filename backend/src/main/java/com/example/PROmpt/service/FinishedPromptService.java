package com.example.PROmpt.service;

import org.springframework.stereotype.Service;

@Service
public interface FinishedPromptService {
    Object getPrompts(Long userId);
}
