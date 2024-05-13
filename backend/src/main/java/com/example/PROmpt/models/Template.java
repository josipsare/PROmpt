package com.example.PROmpt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String filler;

    @Column
    @NonNull
    private String type; //ENUM in future updates

    @Column
    @NonNull
    private String typeExtras;

    @JsonIgnore
    @ManyToMany(mappedBy = "templates")
    private Set<FinishedPrompt> finishedPrompts = new HashSet<>();
}
