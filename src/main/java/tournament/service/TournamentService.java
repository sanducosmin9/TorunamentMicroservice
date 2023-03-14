package tournament.service;

import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.model.Tournament;

import java.util.List;

public interface TournamentService {

    Long createTournament(TournamentDTO tournamentDTO);

    Long updateMatchup(MatchupUpdateRequest request);

    Tournament getTournament(Long tournamentId);

    List<Tournament> getTournaments(int page, int size);
}
