package tournament.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TournamentDTO;
import tournament.dto.TournamentUserDTO;
import tournament.dto.mapper.TournamentDTOMapper;
import tournament.dto.mapper.TournamentUserDTOMapper;
import tournament.service.TournamentService;
import tournament.service.TournamentUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class TournamentController {

    private final TournamentService tournamentService;

    private final TournamentUserService tournamentUserService;

    private final TournamentDTOMapper tournamentDTOMapper;

    private final TournamentUserDTOMapper tournamentUserDTOMapper;

    @GetMapping("/users/{username}")
    public ResponseEntity<TournamentUserDTO> getTournamentsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
                tournamentUserDTOMapper.apply(tournamentUserService.getUserDetails(username))
        );
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Long> createTournament(
            @RequestBody TournamentDTO tournamentDto
    ) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentDto));
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<TournamentDTO> getTournament(
            @PathVariable Long id
    ) {
        var tournament = tournamentService.getTournament(id);
        return ResponseEntity.ok(tournamentDTOMapper.apply(tournament));
    }

    @GetMapping("/tournaments")
    public ResponseEntity<List<TournamentDTO>> getTournaments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var tournaments = tournamentService.getTournaments(page, size);
        return ResponseEntity.ok(
                tournaments.stream()
                        .map(tournamentDTOMapper)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/tournaments")
    public ResponseEntity<Long> updateMatchup(
            @RequestBody MatchupUpdateRequest matchupUpdateRequest
    ) {
        return ResponseEntity.ok(tournamentService.updateMatchup(matchupUpdateRequest));
    }

}
