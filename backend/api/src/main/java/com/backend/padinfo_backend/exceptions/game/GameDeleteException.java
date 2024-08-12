package com.backend.padinfo_backend.exceptions.game;

public class GameDeleteException extends RuntimeException{
    public GameDeleteException(String message) {
        super(message);
    }

    public GameDeleteException(Long id) {
        super("Error al borrar el partido con id: " + id);
    }

    public GameDeleteException(Long id, Throwable exception) {
        super("Error al borrar el partido con id: " + id, exception);
    }
}
