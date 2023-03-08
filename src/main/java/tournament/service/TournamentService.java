package tournament.service;

import tournament.dto.TournamentDTO;
import tournament.model.Tournament;

public interface TournamentService {

    Long createTournament(TournamentDTO tournamentDTO);

    Tournament createMockTournament();

}
