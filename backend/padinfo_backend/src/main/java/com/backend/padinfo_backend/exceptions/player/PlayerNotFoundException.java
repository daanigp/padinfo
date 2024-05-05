package com.backend.padinfo_backend.exceptions.player;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(Long id) {
        super("Jugador no encontrado: " + id);
    }

    public PlayerNotFoundException(String message) {
        super(message);
    }
}
