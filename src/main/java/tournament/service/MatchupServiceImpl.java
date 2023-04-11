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
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchupServiceImpl implements MatchupService {

    private final MatchupRepository matchupRepository;

    @Override
    @Transactional
    public void createAllMatchups(Tournament tournament) {
        var matchups = tournament.getMatchups();
        var teams = tournament.getTeams();
        int numberOfMatchups = teams.size() - 1;
        matchups.addAll(createFirstRoundMatchups(tournament));
        int roundNumber = 1;
        int matchupNumber = teams.size() / 2;
        for (int i = matchupNumber; i < numberOfMatchups; i++) {
            if (i % matchupNumber == 0) {
                roundNumber++;
                matchupNumber /= 2;
            }
            Matchup matchup = new Matchup(0L, tournament, roundNumber, i);
            var persistedMatchup = matchupRepository.save(matchup);
            matchups.add(persistedMatchup);
        }
    }

    private List<Matchup> createFirstRoundMatchups(Tournament tournament) {
        var teams = tournament.getTeams();
        Collections.shuffle(teams);
        List<Matchup> firstRoundMatchups = new ArrayList<>();
        for (int i = 0; i < teams.size() / 2; i++) {
            Matchup matchup = Matchup.builder()
                    .team1(teams.get(i*2))
                    .team2(teams.get(i*2+1))
                    .tournament(tournament)
                    .hasEnded(false)
                    .round(1)
                    .matchupNumber(i)
                    .build();
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
        if (matchup.getTeam2() == null || matchup.getTeam1() == null) {
            throw new MatchupNotCompletedException("There is only one team in this matchup!");
        }
        int nextMatchupNumber = matchup.getMatchupNumber() / 2 + tournament.getSize() / 2;
        var nextMatchup = tournament.getMatchups()
                .stream()
                .filter(it -> it.getMatchupNumber() == nextMatchupNumber)
                .findFirst()
                .orElseThrow(() -> new MatchupNotFoundException("The matchup with this number was not found"));
        if(matchup.getMatchupNumber() % 2 == 0) {
            nextMatchup.setTeam1(winner);
        } else {
            nextMatchup.setTeam2(winner);
        }
    }
}
