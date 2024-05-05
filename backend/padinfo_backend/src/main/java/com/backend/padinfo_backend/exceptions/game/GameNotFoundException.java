package com.backend.padinfo_backend.exceptions.game;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(Long id) {
        super("Partido no encontrado: " + id);
    }

    public GameNotFoundException(String message){
        super(message);
    }
}
