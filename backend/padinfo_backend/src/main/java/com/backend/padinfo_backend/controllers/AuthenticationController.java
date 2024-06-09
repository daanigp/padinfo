package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.Security.JwtTokenProvider;
import com.backend.padinfo_backend.dto.userInfo.Authentication.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.Authentication.LoginUserDTO;
import com.backend.padinfo_backend.exceptions.Response;
import com.backend.padinfo_backend.mappers.UserInfoMapper;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.service.Authentication.IAuthenticationService;
import com.backend.padinfo_backend.model.service.UserInfo.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentificacion padinfo", description = "Autentificación para el uso de la app de padinfo")
public class AuthenticationController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;


    @Operation(summary = "Método que registra un nuevo usuario en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registra el usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody CreateUserDTO createUserDTO) {
        UserInfo user = userInfoMapper.fromDTO(createUserDTO);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<>(Response.noErrorResponse(message), HttpStatus.OK);
    }

    @Operation(summary = "Método que devuelve un token cuando un usuario se loguea correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario logueado",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
        UserInfo loginUser = userInfoMapper.fromDTO(loginUserDTO);
        UserInfo authenticatedUser = authenticationService.authenticate(loginUser);

        String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

        return ResponseEntity.ok(jwtToken);
    }
}
