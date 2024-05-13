package com.example.PROmpt.contoller;


import com.example.PROmpt.service.LLMService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LLMController extends AplicationController{

    private final LLMService llmService;



    public LLMController(LLMService llmService) {
        this.llmService = llmService;
    }
}
