package com.backend.padinfo_backend.controllers;

import com.backend.padinfo_backend.Security.JwtTokenProvider;
import com.backend.padinfo_backend.dto.userInfo.Authentication.CreateUserDTO;
import com.backend.padinfo_backend.dto.userInfo.Authentication.LoginUserDTO;
import com.backend.padinfo_backend.exceptions.Response;
import com.backend.padinfo_backend.mappers.UserInfoMapper;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.service.Authentication.IAuthenticationService;
import com.backend.padinfo_backend.model.service.UserInfo.UserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentificacion_padinfo", description = "Autentificación para el uso de la app de padinfo")
public class AuthenticationController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody CreateUserDTO createUserDTO) {
        UserInfo user = userInfoMapper.fromDTO(createUserDTO);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<>(Response.noErrorResponse(message), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
        UserInfo loginUser = userInfoMapper.fromDTO(loginUserDTO);
        UserInfo authenticatedUser = authenticationService.authenticate(loginUser);

        String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/hola")
    public String saludo() {
        return "AAAAA";
    }
}
