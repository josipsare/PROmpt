package com.example.PROmpt.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "userPrompt")
public class UserPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @Column
    @NonNull
    private String prompt;

    @Column
    @NonNull
    private LocalDateTime date;

    @Column
    @NonNull
    private String type; // ENUM in future updates

    @Column
    @NonNull
    private Double temperature;

    @Column
    @NonNull
    private Boolean takeABreath;

    @Column
    @NonNull
    private String typeExtras;

    @NonNull
    @ManyToOne(optional = false) //jedna prijava ima samo jednog korisnika a jedan korisnik moze imati vise prijava
    @JoinColumn(name = "userId")
    private User user;

}
