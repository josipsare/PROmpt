package com.example.PROmpt.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String text;

    @Column
    @NonNull
    private Integer length;

    @Column
    @NonNull
    private Integer promptTokens;

    @Column
    @NonNull
    private Integer responseTokens;

    @Column
    private Integer grade;

    @NonNull
    @ManyToOne(optional = false) //jedna prijava ima samo jednog korisnika a jedan korisnik moze imati vise prijava
    @JoinColumn(name = "llmId")
    private LLM llm;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userPromptId", referencedColumnName = "id")
    private UserPrompt userPrompt;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "finishedPromptId", referencedColumnName = "id")
    private FinishedPrompt finishedPrompt;


    public Response(@NonNull String text, @NonNull Integer length, @NonNull LLM llm, @NonNull UserPrompt userPrompt, @NonNull FinishedPrompt finishedPrompt, Integer promptTokens, Integer responseTokens) {
        this.text = text;
        this.length = length;
        this.llm = llm;
        this.userPrompt = userPrompt;
        this.finishedPrompt = finishedPrompt;
        this.promptTokens=promptTokens;
        this.responseTokens=responseTokens;
    }
}
