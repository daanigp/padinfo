package com.backend.padinfo_backend.model.service.Tournament;

import com.backend.padinfo_backend.exceptions.tournament.TournamentDeleteException;
import com.backend.padinfo_backend.exceptions.tournament.TournamentNotFoundException;
import com.backend.padinfo_backend.model.entity.Tournament;
import com.backend.padinfo_backend.model.repository.ITournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @InjectMocks
    private TournamentService tournamentService;

    @Mock
    private ITournamentRepository tournamentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(new Tournament(1L, "Boss Barcelona Master Final 2023", "Barcelona", "wpt_barcelona"));
        tournaments.add(new Tournament(2L, "La Rioja Open 1000", "La rioja", "wpt_larioja"));
        tournaments.add(new Tournament(3L, "Boss Vienna Padel Open 2023", "Vienna", "wpt_vienna"));

        when(tournamentRepository.findAll()).thenReturn(tournaments);

        List<Tournament> result = tournamentService.findAll();

        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("La Rioja Open 1000", result.get(1).getName());
        assertEquals("Vienna", result.get(2).getCity());
    }

    @Test
    public void testFindAllTournaments_EmptyList(){
        List<Tournament> tournaments = new ArrayList<>();

        when(tournamentRepository.findAll()).thenReturn(tournaments);

        List<Tournament> result = tournamentService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testFindTournamentById() {
        Tournament tournament = new Tournament(1L, "Boss Barcelona Master Final 2023", "Barcelona", "wpt_barcelona");

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));

        Tournament result = tournamentService.findById(1L);

        assertEquals("Boss Barcelona Master Final 2023", result.getName());
        assertEquals(tournament, result);
    }

    @Test
    public void testFindTournamentById_ThrowsTournamentNotFoundException() {
        Long id = 1L;

        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        TournamentNotFoundException exception = assertThrows(TournamentNotFoundException.class, () -> {
            tournamentService.findById(id);
        });

        assertEquals("Torneo no encontrado: " + id, exception.getMessage());
    }

    @Test
    public void testCreateTournament(){
        Tournament tournament = new Tournament(1L, "Boss Barcelona Master Final 2023", "Barcelona", "wpt_barcelona");

        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

        Tournament result = tournamentService.createTournament(tournament);

        assertEquals("Barcelona", result.getCity());
    }

    @Test
    public void testUpdateTournmanet() {
        Long id = 1L;
        Tournament tournament = new Tournament(id, "Boss Barcelona Master Final 2023", "Barcelona", "wpt_barcelona");
        Tournament newTournament = new Tournament();
        newTournament.setName("Boss Girona Master Final 2023");
        newTournament.setCity("Girona");
        newTournament.setImageURL("wpt_girona");

        when(tournamentRepository.findById(id)).thenReturn(Optional.of(tournament));
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(newTournament);

        Tournament result = tournamentService.updateTournament(id, newTournament);

        assertEquals(id, result.getId());
        assertEquals("Girona", result.getCity());
    }

    @Test
    public void testUpdateTournament_ThrowsTournamentNotFoundException() {
        Long id = 1L;
        Tournament newTournament = new Tournament();
        newTournament.setName("Boss Girona Master Final 2023");
        newTournament.setCity("Girona");
        newTournament.setImageURL("wpt_girona");

        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        TournamentNotFoundException exception = assertThrows(TournamentNotFoundException.class, () -> {
            tournamentService.updateTournament(id, newTournament);
        });

        assertEquals("Error al buscar el torneo -> " + id, exception.getMessage());
    }

    @Test
    public void testDeleteTournament() {
        Long id = 1L;

        tournamentService.deleteTournament(id);

        verify(tournamentRepository).deleteById(id);
    }

    @Test
    public void testDeleteTournament_ThrowsTournamentDeleteException() {
        Long id = 1L;

        RuntimeException exception = new RuntimeException("Deletion failed");
        doThrow(exception).when(tournamentRepository).deleteById(id);

        TournamentDeleteException resultException = assertThrows(TournamentDeleteException.class, () -> {
            tournamentService.deleteTournament(id);
        });

        assertEquals("Error al borrar el torneo con id: " + id, resultException.getMessage());
        assertEquals(exception, resultException.getCause());
    }
}