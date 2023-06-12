package tournament.service;

import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;

public interface MatchupService {
    void createSingleEliminationMatchups(Tournament tournament);

    void createRoundRobinMatchups(Tournament tournament);

    Matchup getMatchup(Long matchupId);

    Team getLoser(Matchup matchup, Team winner);

    void updateMatchupWithWinner(Tournament tournament, Matchup matchup, Team winner);
}
