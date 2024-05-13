package com.backend.padinfo_backend.model.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
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
