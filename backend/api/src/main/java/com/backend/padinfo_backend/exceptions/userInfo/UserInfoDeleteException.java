package com.backend.padinfo_backend.exceptions.userInfo;

public class UserInfoDeleteException extends RuntimeException {
    public UserInfoDeleteException(String message) {
        super(message);
    }

    public UserInfoDeleteException(Long id) {
        super("Error al borrar el usuario con id: " + id);
    }

    public UserInfoDeleteException(Long id, Throwable exception) {
        super("Error al borrar el usuario con id: " + id, exception);
    }
}
