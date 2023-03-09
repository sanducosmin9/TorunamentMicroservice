package tournament.service;

import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.model.Tournament;

public interface TournamentService {

    Long createTournament(TournamentDTO tournamentDTO);

    Long updateMatchup(MatchupUpdateRequest request);

    Tournament createMockTournament();

    Tournament getTournament(Long tournamentId);
}
