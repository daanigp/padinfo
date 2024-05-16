package com.backend.padinfo_backend.model.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

@Hidden
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "ranking_position")
    private Integer rankingPosition;

    @Column
    private String points;

    @Column
    private String name;

    @Column(name = "image_url")
    private String imageURL;

    @Column
    private String gender;
}
