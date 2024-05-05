package com.backend.padinfo_backend.exceptions.tournament;

public class TournamentDeleteException extends RuntimeException{
    public TournamentDeleteException(String message) {
        super(message);
    }

    public TournamentDeleteException(Long id) {
        super("Error al borrar el torneo con id: " + id);
    }

    public TournamentDeleteException(Long id, Throwable exception) {
        super("Error al borrar el torneo con id: " + id, exception);
    }
}
