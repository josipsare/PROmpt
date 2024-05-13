package com.example.PROmpt.service.impl;

import com.example.PROmpt.DTO.ResponseDTOR;
import com.example.PROmpt.DTO.TemplatePrompt;
import com.example.PROmpt.DTO.UserPromptDTO;
import com.example.PROmpt.RecordNotFoundException;
import com.example.PROmpt.contoller.ChatController;
import com.example.PROmpt.models.*;
import com.example.PROmpt.repository.FinishedPromptRepo;
import com.example.PROmpt.repository.TemplateRepo;
import com.example.PROmpt.repository.UserPromptRepo;
import com.example.PROmpt.repository.UserRepo;
import com.example.PROmpt.service.UserPromptService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserPromptServiceImpl implements UserPromptService {

    private final UserPromptRepo userPromptRepo;

    private final ChatController chatController;
    private final FinishedPromptRepo finishedPromptRepo;

    private final TemplateRepo templateRepo;
    private final UserRepo userRepo;

    public UserPromptServiceImpl(UserPromptRepo userPromptRepo, ChatController chatController, FinishedPromptRepo finishedPromptRepo, TemplateRepo templateRepo, UserRepo userRepo) {
        this.userPromptRepo = userPromptRepo;
        this.chatController = chatController;
        this.finishedPromptRepo = finishedPromptRepo;
        this.templateRepo = templateRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Object save(UserPromptDTO userPromptDTO, User currentUser) {
        System.out.println("u save metodi smo");
        System.out.println(currentUser);
        System.out.println(userPromptDTO.getTemperature());
        System.out.println(userPromptDTO.getPrompt());
        UserPrompt userPrompt= new UserPrompt(
                userPromptDTO.getPrompt(),
                LocalDateTime.now(),
                userPromptDTO.getType(),
                userPromptDTO.getTemperature(),
                userPromptDTO.getTakeABreath(),
                userPromptDTO.getTypeExtras(),
                currentUser
        );

        return userPromptRepo.save(userPrompt);
    }

    @Override
    public ResponseDTOR enhance(UserPromptDTO userPromptDTO, String llm, User currentUser) {
        UserPrompt userPrompt = (UserPrompt) save(userPromptDTO, currentUser);
        System.out.println(userPrompt.getType());
        TemplatePrompt templatePrompt=new TemplatePrompt(new HashSet<>(),"error");
        switch (userPrompt.getType()){
            case "code":
                templatePrompt=enhanceCode(userPrompt.getPrompt(), userPrompt.getTypeExtras());
                break;
            case "teaching":
                templatePrompt=enhanceTeaching(userPrompt.getPrompt(), userPrompt.getTypeExtras());
                break;
            case "writing":
                templatePrompt=enhanceWriting(userPrompt.getPrompt(), userPrompt.getTypeExtras());
                break;
            case "rephrasing":
                templatePrompt=enhanceRephrasing(userPrompt.getPrompt(), userPrompt.getTypeExtras());
                break;
            default:
                System.out.println("Invalid type of prompt");
        }


        if (userPrompt.getTakeABreath()){
            templatePrompt.setPrompt(templatePrompt.getPrompt()+"<br>##############################<br>");
            templatePrompt.setPrompt(templatePrompt.getPrompt()+"Take a deep breath and think step-by-step");
        }

        long llmID=0;
        if (llm.equals("ChatGpt-3.5-Turbo")){
            llmID= 1L;
        }else if (llm.equals("LLama-13b")){
            llmID= 2L;
        }


        Set<Template> templatesUsed = templatePrompt.getTemplates();
        String prompt = templatePrompt.getPrompt();

        FinishedPrompt finishedPrompt = new FinishedPrompt(prompt.length(),prompt,userPrompt,templatesUsed);
        FinishedPrompt savedFinishedPrompt=finishedPromptRepo.save(finishedPrompt);

        Response response = (Response) chatController.chat(finishedPrompt,llmID,userPrompt.getTemperature());

        String responseText = response.getText();
        responseText=responseText.replace("\n","<br>");

        ResponseDTOR responseToBeReturned = new ResponseDTOR(savedFinishedPrompt.getText(), responseText, userPrompt.getPrompt());

        return responseToBeReturned;
    }

    @Override
    public Object getPrompts(Long userId) {

        if (userId==null){
            return userPromptRepo.findAll();
        }

        if (userRepo.findById(userId).isEmpty()){
            throw new RecordNotFoundException("User with provided ID doesn't exist");
        }

        User user = userRepo.findById(userId).get();

        return userPromptRepo.findAllByUser(user);
    }

    public TemplatePrompt enhanceRephrasing(String prompt, String typeExtrasString){
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Template> templatesUsed = new HashSet<>();
        try {
            // Parse the JSON string into a Map
            Map<String, String> requestData = objectMapper.readValue(typeExtrasString, new TypeReference<Map<String,String>>() {});

            // Access the fields
            String detail = requestData.get("detail");
            String additional = requestData.get("additional");
            String targetLanguage = requestData.get("targetLanguage");
            System.out.println("Detail: "+detail);
            System.out.println("Additional: "+additional);
            System.out.println("TargetLanguage: "+targetLanguage);

            Template template;
            String filler="";
            String typeExtra="";
            String typeExtraLanguage="";

            typeExtra = switch (additional) {
                case "exaggerate"-> "exaggerate";
                case "illuminate"->"illuminate";
                case "emphasizegood"->"emphasizegood";
                case "emphasizebad"->"emphasizebad";
                case "formalize"->"formalize";
                case "informalize"->"informalize";
                case "paraphrase"->"paraphrase";
                default -> {
                    System.out.println("Invalid additional");
                    yield null;
                }
            };
            if (!(additional == null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("rephrasing", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }

            switch (detail) {
                case "no_extra_detail":
                case "normal_detail":
                case "extra_detail":
                    typeExtra = detail;
                    break;
                default:
                    System.out.println("Invalid detail");
            }
            if (!(detail ==null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("writing", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }
            filler = filler + "\\n My request is: <br>";


            filler = filler.replace("\\n", "<br>##############################<br>");
            prompt= filler+prompt;
            TemplatePrompt templatePrompt = new TemplatePrompt(templatesUsed,prompt);

            return templatePrompt; // Return a success response
        } catch (IOException e) {
            e.printStackTrace();
            return new TemplatePrompt(new HashSet<>(), "error"); // Return a bad request response if parsing fails
        }
    }

    public TemplatePrompt enhanceWriting(String prompt, String typeExtrasString){
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Template> templatesUsed = new HashSet<>();
        try {
            // Parse the JSON string into a Map
            Map<String, String> requestData = objectMapper.readValue(typeExtrasString, new TypeReference<Map<String,String>>() {});

            // Access the fields
            String detail = requestData.get("detail");
            String additional = requestData.get("additional");
            String targetLanguage = requestData.get("targetLanguage");
            System.out.println("Detail: "+detail);
            System.out.println("Additional: "+additional);
            System.out.println("TargetLanguage: "+targetLanguage);

            Template template;
            String filler="";
            String typeExtra="";
            String typeExtraLanguage="";

            typeExtra = switch (additional) {
                case "storyteller"->"storyteller";
                case "poet"->"poet";
                case "essaywriter"->"essaywriter";
                case "journalist"->"journalist";
                case "improve"->"improve";
                default -> {
                    System.out.println("Invalid additional");
                    yield null;
                }
            };
            if (!(additional == null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("writing", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }

            switch (detail) {
                case "no_extra_detail":
                case "normal_detail":
                case "extra_detail":
                    typeExtra = detail;
                    break;
                default:
                    System.out.println("Invalid detail");
            }
            if (!(detail ==null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("writing", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }
            filler = filler + "\\n My request is: <br>";


            filler = filler.replace("\\n", "<br>##############################<br>");
            prompt= filler+prompt;
            TemplatePrompt templatePrompt = new TemplatePrompt(templatesUsed,prompt);

            return templatePrompt; // Return a success response
        } catch (IOException e) {
            e.printStackTrace();
            return new TemplatePrompt(new HashSet<>(), "error"); // Return a bad request response if parsing fails
        }
    }

    public TemplatePrompt enhanceTeaching(String prompt, String typeExtrasString){
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Template> templatesUsed = new HashSet<>();
        try {
            // Parse the JSON string into a Map
            Map<String, String> requestData = objectMapper.readValue(typeExtrasString, new TypeReference<Map<String,String>>() {});

            // Access the fields
            String detail = requestData.get("detail");
            String additional = requestData.get("additional");
            String targetLanguage = requestData.get("targetLanguage");
            System.out.println("Detail: "+detail);
            System.out.println("Additional: "+additional);
            System.out.println("TargetLanguage: "+targetLanguage);

            Template template;
            String filler="";
            String typeExtra="";
            String typeExtraLanguage="";

            typeExtra = switch (additional) {
                case "philosophy"->"philosophy";
                case "history"->"history";
                case "math"->"math";
                case "writing"->"writing";
                default -> {
                    System.out.println("Invalid additional");
                    yield null;
                }
            };
            if (!(additional == null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("teaching", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }

            switch (detail) {
                case "no_extra_detail":
                case "normal_detail":
                case "extra_detail":
                    typeExtra = detail;
                    break;
                default:
                    System.out.println("Invalid detail");
            }
            if (!(detail ==null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("teaching", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }
            filler = filler + "\\n My request is: <br>";
            filler = filler.replace("\\n", "<br>##############################<br>");
            prompt= filler+prompt;
            TemplatePrompt templatePrompt = new TemplatePrompt(templatesUsed,prompt);

            return templatePrompt; // Return a success response
        } catch (IOException e) {
            e.printStackTrace();
            return new TemplatePrompt(new HashSet<>(), "error"); // Return a bad request response if parsing fails
        }
    }

    public TemplatePrompt enhanceCode(String prompt, String typeExtrasString){
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Template> templatesUsed = new HashSet<>();
        try {
            // Parse the JSON string into a Map
            Map<String, String> requestData = objectMapper.readValue(typeExtrasString, new TypeReference<Map<String,String>>() {});

            // Access the fields
            String detail = requestData.get("detail");
            String additional = requestData.get("additional");
            String targetLanguage = requestData.get("targetLanguage");
            System.out.println("Detail: "+detail);
            System.out.println("Additional: "+additional);
            System.out.println("TargetLanguage: "+targetLanguage);

            Template template;
            String filler="";
            String typeExtra="";
            String typeExtraLanguage="";

            typeExtra = switch (additional) {
                case "translate" -> {
                    typeExtraLanguage = switch (targetLanguage) {
                        case "java" -> "java";
                        case "python" -> "python";
                        case "c" -> "c";
                        case "javascript" -> "javascript";
                        default -> typeExtraLanguage;
                    };
                    yield "translate";
                }
                case "explain" -> "explain";
                case "debug" -> "debug";
                case "write" -> "write";
                case "security" -> "security";
                case "fullstackdev" -> "fullstackdev";
                case "uxui" -> "uxui";
                case "frontenddev" -> "frontenddev";
                case "backenddev" -> "backenddev";
                case "data_anal" -> "data_anal";
                default -> {
                    System.out.println("Invalid additional");
                    yield null;
                }
            };
            if (!(additional == null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("code", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }
            if(!(targetLanguage ==null)){
                template = templateRepo.findAllByTypeAndTypeExtras("code", typeExtraLanguage).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }

            switch (detail) {
                case "no_extra_detail":
                case "normal_detail":
                case "extra_detail":
                    typeExtra = detail;
                    break;
                default:
                    System.out.println("Invalid detail");
            }
            if (!(detail ==null)) {
                template = templateRepo.findAllByTypeAndTypeExtras("code", typeExtra).getFirst();
                filler += template.getFiller();
                templatesUsed.add(template);
            }
            filler = filler + "\\n My request is: <br>";
            filler = filler.replace("\\n", "<br>##############################<br>");
            prompt= filler+prompt;
            TemplatePrompt templatePrompt = new TemplatePrompt(templatesUsed,prompt);

            return templatePrompt; // Return a success response
        } catch (IOException e) {
            e.printStackTrace();
            return new TemplatePrompt(new HashSet<>(), "error"); // Return a bad request response if parsing fails
        }
    }
    //
    //mozda da je imati odvojenu metodu za svaki tip,
    //tako da minimiziramo nepotrebne informacija i organizacija bude bolja
}
