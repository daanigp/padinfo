package com.backend.padinfo_backend.dto.tournament;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateUpdateTournamentDTO implements Serializable {
    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre del torneo")
    private String name;

    @NotBlank
    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Nombre de la ciudad")
    private String city;

    @NotBlank
    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Imágen del torneo")
    private String imageURL;
}
