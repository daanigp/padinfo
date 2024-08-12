package com.backend.padinfo_backend.dto.userInfo.Authentication;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class LoginUserDTO implements Serializable {
    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Nombre de usuario")
    private String username;

    @NotEmpty(message = "No puede estar vacío")
    @NotBlank
    @Schema(description = "Contraseña")
    private String password;
}
