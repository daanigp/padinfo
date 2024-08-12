package com.backend.padinfo_backend.dto.userInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDTO implements Serializable {

    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Nombre del usuario")
    private String name;

    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Apellidos del usuario")
    private String lastname;

    @Email(message = "El formato del email no es correcto")
    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Correo del usuario")
    private String email;

    @NotEmpty(message = "No puede estar vacío")
    @Schema(description = "Imagen del usuario")
    private String imageURL;
}
