package com.backend.padinfo_backend.model.service.Authentication;

import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.repository.IUserInfoRepository;
import com.backend.padinfo_backend.model.service.UserInfo.IUserInfoService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
@Hidden
public class AuthenticationService implements  IAuthenticationService{

    @Autowired
    private IUserInfoRepository userInfoRepository;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserInfo signup(UserInfo newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setIsConnected(0);

        if (newUser.getImageURL() == null || newUser.getImageURL().isBlank()) {
            newUser.setImageURL("R.drawable.imgperfil_basic");
        }

        return userInfoRepository.save(newUser);
    }

    @Override
    public UserInfo authenticate(UserInfo user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );


        //userInfoService.updateIsConnected(logedUser.getId());

        /*return userInfoRepository.findById(user.getId()).orElseThrow(
                () -> new UserInfoNotFoundException(user.getId())
        );*/

        return userInfoRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new UserInfoNotFoundException("Usuario no encontrado con nombre: " + user.getUsername())
        );
    }
}
