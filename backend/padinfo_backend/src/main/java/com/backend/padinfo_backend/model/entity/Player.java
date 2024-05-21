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

    @Column(name = "ranking_position", nullable = false)
    private Integer rankingPosition;

    @Column(nullable = false)
    private String points;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(nullable = false)
    private String gender;
}
