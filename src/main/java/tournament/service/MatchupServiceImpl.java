package tournament.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tournament.exceptions.MatchupNotCompletedException;
import tournament.exceptions.MatchupNotFoundException;
import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;
import tournament.repository.MatchupRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchupServiceImpl implements MatchupService {

    private final MatchupRepository matchupRepository;

    @Override
    @Transactional
    public List<Matchup> createFirstRoundMatchups(Tournament tournament) {
        var teams = tournament.getTeams();
        Collections.shuffle(teams);
        List<Matchup> firstRoundMatchups = new ArrayList<>();
        for (int i = 0; i < teams.size() / 2; i++) {
            Matchup matchup = new Matchup(
                    0L,
                    teams.get(i * 2),
                    teams.get(i * 2 + 1),
                    null,
                    null,
                    tournament,
                    false,
                    1,
                    i
            );
            var persistedMatchup = matchupRepository.save(matchup);
            teams.get(i * 2).setActiveMatchup(persistedMatchup);
            teams.get(i * 2 + 1).setActiveMatchup(persistedMatchup);
            firstRoundMatchups.add(persistedMatchup);
        }
        return firstRoundMatchups;
    }

    @Override
    public Matchup getMatchup(Long matchupId) {
        return matchupRepository.findById(matchupId)
                .orElseThrow(() -> new MatchupNotFoundException("Matchup with id " + matchupId + " was not found"));
    }

    @Override
    public Team getLoser(Matchup matchup, Team winner) {
        if (matchup.getTeam1() == winner) {
            matchup.setLoser(matchup.getTeam2());
            return matchup.getTeam2();
        } else {
            matchup.setLoser(matchup.getTeam1());
            return matchup.getTeam1();
        }
    }

    @Override
    @Transactional
    public void updateMatchupWithWinner(Tournament tournament, Matchup matchup, Team winner) {
        if (matchup.getTeam2() == null) {
            throw new MatchupNotCompletedException("There is only one team in this matchup!");
        }
        int neighborMatchupIndex = this.getNeighborMatchupIndex(tournament, matchup);
        Matchup neighborMatchup = tournament.getMatchups().get(neighborMatchupIndex);
        if (!neighborMatchup.isHasEnded()) {
            Matchup newMatchup = new Matchup();
            if (neighborMatchupIndex % 2 == 1) {
                newMatchup.setTeam1(winner);
                newMatchup.setMatchupNumber(tournament.getSize() / 2 + neighborMatchupIndex);
            } else {
                newMatchup.setTeam2(winner);
                newMatchup.setMatchupNumber(tournament.getSize() / 2 + neighborMatchupIndex - 1);
            }
            newMatchup.setHasEnded(false);
            newMatchup.setTournament(tournament);
            newMatchup.setId(0L);
            newMatchup.setRound(matchup.getRound() + 1);
            var persistedMatchup = matchupRepository.save(newMatchup);
            winner.setActiveMatchup(persistedMatchup);
        } else {
            var activeMatchup = neighborMatchup.getWinner().getActiveMatchup();
            if (neighborMatchupIndex % 2 == 0) {
                activeMatchup.setTeam1(winner);
            } else {
                activeMatchup.setTeam2(winner);
            }
        }
    }

    private int getNeighborMatchupIndex(Tournament tournament, Matchup matchup) {
        var matchups = tournament.getMatchups();
        var currentMatchupIndex = matchups.indexOf(matchup);
        return currentMatchupIndex % 2 == 0 ? currentMatchupIndex + 1 : currentMatchupIndex - 1;
    }

}
