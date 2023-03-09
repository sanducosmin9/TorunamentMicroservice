package tournament.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.exceptions.MatchupNotFoundException;
import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;
import tournament.repository.MatchupRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchupServiceImpl implements MatchupService{

    private final MatchupRepository matchupRepository;

    @Override
    @Transactional
    public List<Matchup> createFirstRoundMatchups(Tournament tournament) {
        var teams = tournament.getTeams();
        Collections.shuffle(teams);
        List<Matchup> firstRoundMatchups = new ArrayList<>();
        for(int i = 0; i < teams.size() / 2; i++) {
            Matchup matchup = new Matchup(
                    0L,
                    teams.get(i * 2),
                    teams.get(i * 2 + 1),
                    null,
                    null,
                    tournament,
                    false
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
        if(matchup.getTeam1() == winner) {
            matchup.setLoser(matchup.getTeam2());
            return matchup.getTeam2();
        } else {
            matchup.setLoser(matchup.getTeam1());
            return matchup.getTeam1();
        }
    }
}
