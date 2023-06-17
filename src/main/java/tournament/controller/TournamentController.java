package tournament.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.dto.TournamentUserDTO;
import tournament.dto.TournamentsDTO;
import tournament.dto.mapper.TournamentDTOMapper;
import tournament.dto.mapper.TournamentUserDTOMapper;
import tournament.service.TournamentService;
import tournament.service.TournamentUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TournamentController {

    private final TournamentService tournamentService;

    private final TournamentUserService tournamentUserService;

    private final TournamentDTOMapper tournamentDTOMapper;

    private final TournamentUserDTOMapper tournamentUserDTOMapper;

    @GetMapping("/alive")
    public boolean isAlive() {
        return true;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<TournamentUserDTO> getTournamentsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
                tournamentUserDTOMapper.apply(tournamentUserService.getUserDetails(username))
        );
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Long> createSingleEliminationTournament(
            @RequestBody TournamentDTO tournamentDto
    ) {
        if(tournamentDto.getType().equals("robin")) {
            return ResponseEntity.ok(tournamentService.createRoundRobinTournament(tournamentDto));
        } else if(tournamentDto.getType().equals("single")) {
            return ResponseEntity.ok(tournamentService.createSingleEliminationTournament(tournamentDto));
        }
        return ResponseEntity.ok(0L);
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<TournamentDTO> getTournament(
            @PathVariable Long id
    ) {
        var tournament = tournamentService.getTournament(id);
        return ResponseEntity.ok(tournamentDTOMapper.apply(tournament));
    }

    @GetMapping("/tournaments")
    public ResponseEntity<TournamentsDTO> getTournaments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var tournaments = tournamentService.getTournaments(page - 1, size);
        return ResponseEntity.ok(
                new TournamentsDTO(
                        tournaments.stream()
                                .map(tournamentDTOMapper)
                                .collect(Collectors.toList()),
                        tournamentService.getNumberOfTournaments()
                )
        );
    }

    @PutMapping("/tournaments")
    public ResponseEntity<Long> updateSingleEliminationMatchup(
            @RequestBody MatchupUpdateRequest matchupUpdateRequest
    ) {
        return ResponseEntity.ok(tournamentService.updateMatchup(matchupUpdateRequest));
    }



}
