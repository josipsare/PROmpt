package com.example.PROmpt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "finishedPrompt")
public class FinishedPrompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @Column
    @NonNull
    private int length;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String text;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userPromptId", referencedColumnName = "id")
    @JsonIgnore
    private UserPrompt userPrompt;

    @NonNull
    @ManyToMany
    @JoinTable(name = "finished_prompt_template",
            joinColumns = @JoinColumn(name = "finished_prompt_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id"))
    private Set<Template> templates = new HashSet<>();

    public FinishedPrompt(@NonNull int length, @NonNull String text, @NonNull UserPrompt userPrompt, @NonNull Set<Template> templates) {
        this.length = length;
        this.text = text;
        this.userPrompt = userPrompt;
        this.templates = templates;
    }

    @Override
    public String toString() {
        return "FinishedPrompt{" +
                "Id=" + Id +
                ", length=" + length +
                ", text='" + text + '\'' +
                ", userPrompt=" + userPrompt +
                ", templates=" + templates +
                '}';
    }
}
