package com.backend.padinfo_backend.model.service.UserInfo;

import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Hidden
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoService.findByUsername(username).orElseThrow(
                () -> new UserInfoNotFoundException("Usuario no encontrado con nombre de usuario:" + username)
        );
    }

    public UserDetails loadById(Long id) {
        return userInfoService.findById(id);
    }
}
