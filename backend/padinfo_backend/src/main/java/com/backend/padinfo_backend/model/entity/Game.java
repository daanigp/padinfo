package com.backend.padinfo_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
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
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, name = "name_player1")
    private String namePlayer1;

    @Column(nullable = false, name = "name_player2")
    private String namePlayer2;

    @Column(nullable = false, name = "name_player3")
    private String namePlayer3;

    @Column(nullable = false, name = "name_player4")
    private String namePlayer4;

    @Column(nullable = false, name = "set1_points_t1")
    private Integer set1PointsT1;

    @Column(nullable = false, name = "set2_points_t1")
    private Integer set2PointsT1;

    @Column(nullable = false, name = "set3_points_t1")
    private Integer set3PointsT1;

    @Column(nullable = false, name = "set1_points_t2")
    private Integer set1PointsT2;

    @Column(nullable = false, name = "set2_points_t2")
    private Integer set2PointsT2;

    @Column(nullable = false, name = "set3_points_t2")
    private Integer set3PointsT2;

    @Column(nullable = false, name = "winner_team")
    private Integer winnerTeam;

    @JsonIgnoreProperties("games")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;
}
