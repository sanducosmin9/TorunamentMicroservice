package tournament.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.exceptions.TournamentNotFoundException;
import tournament.model.Matchup;
import tournament.model.Team;
import tournament.model.Tournament;
import tournament.repository.MatchupRepository;
import tournament.repository.TeamRepository;
import tournament.repository.TournamentRepository;
import tournament.repository.TournamentUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final TournamentUserRepository userRepository;

    private final TournamentRepository tournamentRepository;

    private final TeamRepository teamRepository;

    private final MatchupRepository matchupRepository;

    private final MatchupService matchupService;

    private final TeamService teamService;

    @Override
    @Transactional
    public Long createTournament(TournamentDTO tournamentDTO) {
        var username = tournamentDTO.getOwner().getUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " was not found"));

        var persistedTeams = tournamentDTO
                .getTeams()
                .stream()
                .map(it -> teamRepository.save(new Team(0L, it.getName(), null, null)))
                .toList();

        Tournament tournament = new Tournament(
                0L,
                owner,
                tournamentDTO.getName(),
                persistedTeams.size(),
                tournamentDTO.getCreationDate(),
                new ArrayList<>(),
                persistedTeams,
                null
        );
        var persistedTournament = tournamentRepository.save(tournament);
        owner.getTournaments().add(persistedTournament);
        persistedTeams.forEach(it -> it.setTournament(persistedTournament));
        persistedTournament.getMatchups().addAll(matchupService.createFirstRoundMatchups(persistedTournament));
        return persistedTournament.getId();
    }

    @Override
    @Transactional
    public Long updateMatchup(MatchupUpdateRequest request) {
        var tournament = this.getTournament(request.getTournamentId());
        var winner = teamService.getTeam(request.getWinnerId());
        var endedMatchup = matchupService.getMatchup(request.getMatchupId());
        var loser = matchupService.getLoser(endedMatchup, winner);
        if(isFinalRound(tournament)) {
            endedMatchup.setHasEnded(true);
            endedMatchup.setWinner(winner);
            endedMatchup.setLoser(loser);
            tournament.setWinner(winner);
            return tournament.getId();
        }
        Matchup neighborMatchup = this.getNeighborMatchup(tournament, endedMatchup);
        if(!neighborMatchup.isHasEnded()) {
            Matchup newMatchup = new Matchup(
                    0L,
                    winner,
                    null,
                    null,
                    null,
                    tournament,
                    false
            );
            var persistedMatchup = matchupRepository.save(newMatchup);
            winner.setActiveMatchup(persistedMatchup);
        } else {
            var activeMatchup = neighborMatchup.getWinner().getActiveMatchup();
            activeMatchup.setTeam2(winner);
            matchupRepository.save(activeMatchup);
        }
        endedMatchup.setHasEnded(true);
        endedMatchup.setWinner(winner);
        endedMatchup.setLoser(loser);
        return tournament.getId();
    }

    private boolean isFinalRound(Tournament tournament) {
        return tournament.getMatchups().stream()
                .filter(it -> !it.isHasEnded())
                .count() == 1;
    }

    private Matchup getNeighborMatchup(Tournament tournament, Matchup matchup) {
        long neighborMatchupId = matchup.getId() % 2 == 0 ? matchup.getId() + 1 : matchup.getId() - 1;
        return matchupService.getMatchup(neighborMatchupId);
    }

    @Override
    public Tournament getTournament(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() ->
                        new TournamentNotFoundException("Tournament with id " + tournamentId + " was not found")
                );
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
