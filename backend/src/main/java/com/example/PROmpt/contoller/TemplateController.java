package com.example.PROmpt.contoller;

import com.example.PROmpt.service.TemplateService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController extends AplicationController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }
}
