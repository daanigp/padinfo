package com.backend.padinfo_backend.dto.game;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO implements Serializable {
    private Long id;
    private String namePlayer1;
    private String namePlayer2;
    private String namePlayer3;
    private String namePlayer4;
    private Integer set1PointsT1;
    private Integer set2PointsT1;
    private Integer set3PointsT1;
    private Integer set1PointsT2;
    private Integer set2PointsT2;
    private Integer set3PointsT2;
    private Integer winnerTeam;
}
