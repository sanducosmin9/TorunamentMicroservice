package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;
import tournament.repository.TournamentRepository;
import tournament.repository.TournamentUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final TournamentUserRepository userRepository;

    private final TournamentRepository tournamentRepository;

    @Override
    public Tournament createTournament() {
        Tournament tournament = Tournament.builder()
                .name("Default")
                .size(16)
                .creationDate(LocalDateTime.now())
                .build();
        var savedTournament = tournamentRepository.save(tournament);
        savedTournament.setMatchups(
                List.of(
                        mockMatchup(savedTournament, "T1","T2", true),
                        mockMatchup(savedTournament, "T3","T4", true),
                        mockMatchup(savedTournament, "T5","T6", true),
                        mockMatchup(savedTournament, "T7","T8", true),
                        mockMatchup(savedTournament, "T1","T3", true),
                        mockMatchup(savedTournament, "T5","T7", false),
                        mockMatchup(savedTournament, "TBD", "TBD", false)
                )
        );
        return savedTournament;
    }

    private Matchup mockMatchup(Tournament tournament, String t1, String t2, boolean hasEnded) {
        return Matchup.builder()
                .hasEnded(hasEnded)
                .tournament(tournament)
                .loser(mockTeam(t1))
                .winner(mockTeam(t2))
                .build();
    }

    private Team mockTeam(String teamName) {
        return Team.builder()
                .name(teamName).build();
    }


}
