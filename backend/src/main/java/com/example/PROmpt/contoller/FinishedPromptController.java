package com.example.PROmpt.contoller;


import com.example.PROmpt.service.FinishedPromptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinishedPromptController extends AplicationController {

    private final FinishedPromptService finishedPromptService;

    public FinishedPromptController(FinishedPromptService finishedPromptService) {
        this.finishedPromptService = finishedPromptService;
    }

    @GetMapping("/finishedPrompts")
    public ResponseEntity<Object> userPrompts(@RequestParam(required = false) Long userId){
        return new ResponseEntity<>(finishedPromptService.getPrompts(userId), HttpStatus.OK);
    }


    @GetMapping("/myFinishedPrompts")
    public ResponseEntity<Object> myPrompts(){
        return new ResponseEntity<>(finishedPromptService.getPrompts(currentUser.getId()),HttpStatus.OK);
    }
}
