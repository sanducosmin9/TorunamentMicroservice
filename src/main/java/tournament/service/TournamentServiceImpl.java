package tournament.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tournament.dto.TournamentDTO;
import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;
import tournament.repository.TeamRepository;
import tournament.repository.TournamentRepository;
import tournament.repository.TournamentUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final TournamentUserRepository userRepository;

    private final TournamentRepository tournamentRepository;

    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public Long createTournament(TournamentDTO tournamentDTO) {
        var username = tournamentDTO.getOwner().getUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " was not found"));

        var persistedTeams = tournamentDTO
                .getTeams()
                .stream()
                .map(it -> teamRepository.save(new Team(0L, it.getName(), null)))
                .toList();

        Tournament tournament = new Tournament(
                0L,
                owner,
                tournamentDTO.getName(),
                tournamentDTO.getSize(),
                tournamentDTO.getCreationDate(),
                new ArrayList<>(),
                persistedTeams
        );
        var persistedTournament = tournamentRepository.save(tournament);
        owner.getTournaments().add(persistedTournament);
        persistedTeams.forEach(it -> it.setTournament(persistedTournament));
        return persistedTournament.getId();
    }


    @Override
    public Tournament createMockTournament() {
        Tournament tournament = Tournament.builder()
                .name("Default")
                .size(16)
                .creationDate(LocalDateTime.now())
                .build();
        var savedTournament = tournamentRepository.save(tournament);
        savedTournament.setMatchups(
                List.of(
                        mockMatchup(savedTournament, "T1", "T2", true),
                        mockMatchup(savedTournament, "T3", "T4", true),
                        mockMatchup(savedTournament, "T5", "T6", true),
                        mockMatchup(savedTournament, "T7", "T8", true),
                        mockMatchup(savedTournament, "T1", "T3", true),
                        mockMatchup(savedTournament, "T5", "T7", false),
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
