package com.backend.padinfo_backend.model.service.Authentication;

import com.backend.padinfo_backend.model.entity.UserInfo;

public interface IAuthenticationService {
    public UserInfo signup(UserInfo newUser);
    public UserInfo authenticate(UserInfo user);
}
