package com.backend.padinfo_backend.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameDTO implements Serializable {

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del jugador 1")
    private String namePlayer1;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del jugador 2")
    private String namePlayer2;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del jugador 3")
    private String namePlayer3;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del jugador 4")
    private String namePlayer4;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 1 - Equipo 1")
    private Integer set1PointsT1;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 2 - Equipo 1")
    private Integer set2PointsT1;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 3 - Equipo 1")
    private Integer set3PointsT1;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 1 - Equipo 2")
    private Integer set1PointsT2;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 2 - Equipo 2")
    private Integer set2PointsT2;

    @Min(value = 0, message = "El valor mínimo debe ser 1")
    @Max(value = 7, message = "El valor máximo debe ser 7")
    @Schema(description = "Puntos del set 3 - Equipo 2")
    private Integer set3PointsT2;

    @Min(value = 1, message = "El valor mínimo debe ser 1")
    @Max(value = 2, message = "El valor máximo debe ser 2")
    @Schema(description = "Equipo que ha ganado el partido (1 o 2)")
    private Integer winnerTeam;

    @Schema(description = "Id del usuario")
    private Long user_id;
}
