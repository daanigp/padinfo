package com.backend.padinfo_backend.dto.player;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
public class CreateUpdatePlayerDTO implements Serializable {
    @Min(value = 1, message = "El valor mínimo debe ser 1")
    @Schema(description = "Posición del ranking del jugador/a")
    private Integer rankingPosition;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Puntos del jugador/a")
    private String points;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del jugador/a")
    private String name;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Imágen del jugador/a")
    private String imageURL;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Género del jugador/a")
    private String gender;
}

