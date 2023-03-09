package tournament.service;

import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;

import java.util.List;

public interface MatchupService {
    List<Matchup> createFirstRoundMatchups(Tournament tournament);

    Matchup getMatchup(Long matchupId);

    Team getLoser(Matchup matchup, Team winner);
}
