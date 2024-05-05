package com.backend.padinfo_backend.model.service.Tournament;

import com.backend.padinfo_backend.exceptions.tournament.TournamentDeleteException;
import com.backend.padinfo_backend.exceptions.tournament.TournamentNotFoundException;
import com.backend.padinfo_backend.model.entity.Tournament;
import com.backend.padinfo_backend.model.repository.ITournamentRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class TournamentService implements ITournamentService{

    @Autowired
    private ITournamentRepository tournamentRepository;

    @Override
    public List<Tournament> findAll() {
        return (List<Tournament>) tournamentRepository.findAll();
    }

    @Override
    public Tournament findById(long id) {
        return tournamentRepository.findById(id).orElseThrow(
                () -> new TournamentNotFoundException(id)
        );
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament updateTournament(long id, Tournament newTournament) {

        Tournament tournament = tournamentRepository.findById(id).orElseThrow(
                () -> new TournamentNotFoundException("Error al buscar el torneo -> " + id)
        );

        newTournament.setId(tournament.getId());

        return tournamentRepository.save(newTournament);
    }

    @Override
    public void deleteTournament(long id) {
        try {
            tournamentRepository.deleteById(id);
        } catch (Exception ex) {
            throw new TournamentDeleteException(id, ex);
        }
    }
}
