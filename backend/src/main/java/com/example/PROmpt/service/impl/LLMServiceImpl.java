package com.example.PROmpt.service.impl;

import com.example.PROmpt.DTO.ResponseDTOR;
import com.example.PROmpt.models.Response;
import com.example.PROmpt.repository.ResponseRepo;
import com.example.PROmpt.service.LLMService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LLMServiceImpl implements LLMService {

    private final ResponseRepo responseRepo;

    public LLMServiceImpl(ResponseRepo responseRepo) {
        this.responseRepo = responseRepo;
    }


}
