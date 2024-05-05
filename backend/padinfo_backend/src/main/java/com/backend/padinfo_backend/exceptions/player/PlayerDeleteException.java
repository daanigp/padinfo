package com.backend.padinfo_backend.exceptions.player;

public class PlayerDeleteException extends RuntimeException{
    public PlayerDeleteException(String message) {
        super(message);
    }

    public PlayerDeleteException(Long id) {
        super("Error al borrar el jugador con id: " + id);
    }

    public PlayerDeleteException(Long id, Throwable exception) {
        super("Error al borrar el jugador con id: " + id, exception);
    }
}
