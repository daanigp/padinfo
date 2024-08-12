package com.backend.padinfo_backend.model.service.Tournament;

import com.backend.padinfo_backend.model.entity.Tournament;

import java.util.List;

public interface ITournamentService {
    List<Tournament> findAll();
    Tournament findById(long id);
    Tournament createTournament(Tournament tournament);
    Tournament updateTournament(long id, Tournament newTournament);
    void deleteTournament(long id);
}
