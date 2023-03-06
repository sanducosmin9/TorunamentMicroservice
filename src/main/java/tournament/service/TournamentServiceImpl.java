package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.exceptions.UserNotFoundException;
import tournament.model.Matchup;
import tournament.model.Member;
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
                        mockMatchup(savedTournament, "T1", "M1", "T2", "M2", true),
                        mockMatchup(savedTournament, "T3", "M3", "T4", "M4", true),
                        mockMatchup(savedTournament, "T5", "M5", "T6", "M6", true),
                        mockMatchup(savedTournament, "T7", "M7", "T8", "M8", true),
                        mockMatchup(savedTournament, "T1", "M1", "T3", "M3", true),
                        mockMatchup(savedTournament, "T5", "M5", "T7", "M7", false),
                        mockMatchup(savedTournament, "TBD", "TBD", "TBD", "TBD", false)
                )
        );
        return savedTournament;
    }

    private Matchup mockMatchup(Tournament tournament, String t1, String m1, String t2, String m2, boolean hasEnded) {
        return Matchup.builder()
                .hasEnded(hasEnded)
                .tournament(tournament)
                .loser(mockTeam(t1, m1))
                .winner(mockTeam(t2, m2))
                .build();
    }

    private Team mockTeam(String teamName, String memberName) {
        return Team.builder()
                .members(List.of(Member.builder().name(memberName).build()))
                .name(teamName).build();
    }


}
