package tournament.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.exceptions.TournamentNotFoundException;
import tournament.model.*;
import tournament.repository.ScoreboardRepository;
import tournament.repository.TeamRepository;
import tournament.repository.TournamentRepository;
import tournament.repository.TournamentUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final TournamentUserRepository userRepository;

    private final TournamentRepository tournamentRepository;

    private final TeamRepository teamRepository;

    private final ScoreboardRepository scoreboardRepository;

    private final MatchupService matchupService;

    private final TeamService teamService;

    @Override
    @Transactional
    public Long createSingleEliminationTournament(TournamentDTO tournamentDTO) {
        var username = tournamentDTO.getUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " was not found"));

        var persistedTeams = tournamentDTO
                .getTeams()
                .stream()
                .map(it -> teamRepository.save(new Team(0L, it.getName(), null, null, null)))
                .toList();

        Tournament tournament = new Tournament(
                0L,
                owner,
                tournamentDTO.getName(),
                persistedTeams.size(),
                LocalDateTime.now(),
                tournamentDTO.getGame(),
                new ArrayList<>(),
                persistedTeams,
                null,
                null
        );
        var persistedTournament = tournamentRepository.save(tournament);
        owner.getTournaments().add(persistedTournament);
        persistedTeams.forEach(it -> it.setTournament(persistedTournament));
        matchupService.createSingleEliminationMatchups(persistedTournament);
        return persistedTournament.getId();
    }

    @Override
    public Long createRoundRobinTournament(TournamentDTO tournamentDTO) {
        var username = tournamentDTO.getUsername();
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " was not found"));
        var persistedTeams = tournamentDTO
                .getTeams()
                .stream()
                .map(it -> teamRepository.save(new Team(0L, it.getName(), null, null, null)))
                .toList();
        Tournament tournament = new Tournament(
                0L,
                owner,
                tournamentDTO.getName(),
                persistedTeams.size(),
                LocalDateTime.now(),
                tournamentDTO.getGame(),
                new ArrayList<>(),
                persistedTeams,
                null,
                null
        );
        var persistedTournament = tournamentRepository.save(tournament);
        owner.getTournaments().add(persistedTournament);
        persistedTeams.forEach(it -> it.setTournament(persistedTournament));
        matchupService.createRoundRobinMatchups(persistedTournament);
        List<Scoreboard> scoreboards = new ArrayList<>();
        for(Team team: persistedTeams) {
            Scoreboard scoreboard = Scoreboard.builder()
                    .tournament(persistedTournament)
                    .team(team)
                    .wins(0L).losses(0L)
                    .build();
            scoreboards.add(scoreboardRepository.save(scoreboard));
        }
        persistedTournament.setScoreboard(scoreboards);
        return persistedTournament.getId();
    }

    @Override
    @Transactional
    public Long updateMatchup(MatchupUpdateRequest request) {
        var tournament = this.getTournament(request.getTournamentId());
        var winner = teamService.getTeam(request.getWinnerId());
        var matchup = matchupService.getMatchup(request.getMatchupId());
        var loser = matchupService.getLoser(matchup, winner);
        if(tournament.getScoreboard() == null) {
            if(isFinalRound(tournament)) {
                tournament.setWinner(winner);
            } else {
                matchupService.updateMatchupWithWinner(tournament, matchup, winner);
            }
        } else {
            tournament.getScoreboard().stream()
                    .filter(it -> it.getTeam().getId().equals(winner.getId()))
                    .findFirst()
                    .ifPresent(it -> it.setWins(it.getWins() + 1));
            tournament.getScoreboard().stream()
                    .filter(it -> it.getTeam().getId().equals(loser.getId()))
                    .findFirst()
                    .ifPresent(it -> it.setLosses(it.getWins() + 1));
            if(isFinalRound(tournament)) {
                var tournamentWinner = tournament.getScoreboard().stream()
                        .max(Comparator.comparingLong(Scoreboard::getWins))
                        .map(Scoreboard::getTeam)
                        .orElse(null);
                tournament.setWinner(tournamentWinner);
            }
        }
        endMatchup(matchup, winner, loser);
        return tournament.getId();

    }

    private void endMatchup(Matchup matchup, Team winner, Team loser) {
        matchup.setHasEnded(true);
        matchup.setWinner(winner);
        matchup.setLoser(loser);
    }

    private boolean isFinalRound(Tournament tournament) {
        return tournament.getMatchups().stream()
                .filter(it -> !it.isHasEnded())
                .count() == 1;
    }

    @Override
    public Tournament getTournament(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() ->
                        new TournamentNotFoundException("Tournament with id " + tournamentId + " was not found")
                );
    }

    @Override
    public List<Tournament> getTournaments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tournamentRepository.findTournamentsByWinnerIsNull(pageable);
    }

    @Override
    public Long getNumberOfTournaments() {
        return (long) tournamentRepository.findAll().size();
    }
}
