package com.backend.padinfo_backend.exceptions.userInfo;

public class UserInfoNotFoundException extends RuntimeException{
    public UserInfoNotFoundException(Long id) {
        super("Ususario no encontrado: " + id);
    }

    public UserInfoNotFoundException(String message) {
        super(message);
    }
}
