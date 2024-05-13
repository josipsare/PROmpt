package com.example.PROmpt.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserPromptDTO {
    public UserPromptDTO(String prompt , String type,String typeExtras) {
        this.prompt = prompt;
        this.type = type;
        this.typeExtras=typeExtras;
    }

    private String prompt;
    private String type;
    private Double temperature;
    private Boolean takeABreath;
    private String typeExtras;


}
