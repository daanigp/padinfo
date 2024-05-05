package com.backend.padinfo_backend.exceptions.tournament;

public class TournamentNotFoundException extends RuntimeException{
    public TournamentNotFoundException(Long id) {
        super("Torneo no encontrado: " + id);
    }

    public TournamentNotFoundException(String message) {
        super(message);
    }
}
