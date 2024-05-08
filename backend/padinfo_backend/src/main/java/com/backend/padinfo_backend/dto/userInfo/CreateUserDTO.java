package com.backend.padinfo_backend.dto.userInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO implements Serializable {
    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre de usuario")
    private String user;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Contraseña")
    private String password;

    @Schema(description = "Nombre del usuario")
    private String name;

    @Schema(description = "Apellidos del usuario")
    private String lastname;

    @Email(message = "El formato del email no es correcto")
    @Schema(description = "Correo del usuario")
    private String email;
}
