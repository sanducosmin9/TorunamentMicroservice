package tournament.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournament.dto.MatchupUpdateRequest;
import tournament.dto.TeamDTO;
import tournament.dto.TournamentDTO;
import tournament.dto.mapper.TournamentDTOMapper;
import tournament.model.Tournament;
import tournament.service.TournamentService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/tournament")
@RequiredArgsConstructor
@CrossOrigin
public class TournamentController {

    private final TournamentService tournamentService;

    private final TournamentDTOMapper tournamentDTOMapper;

    // get tournament by owner id

    @PostMapping
    public ResponseEntity<Long> createTournament(
            @RequestBody TournamentDTO tournamentDto
    ) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournament(
            @PathVariable Long id
    ) {
        var tournament = tournamentService.getTournament(id);
        return ResponseEntity.ok(tournamentDTOMapper.apply(tournament));
    }

    @PutMapping
    public ResponseEntity<Long> updateMatchup(
            @RequestBody MatchupUpdateRequest matchupUpdateRequest
    ) {
        return ResponseEntity.ok(tournamentService.updateMatchup(matchupUpdateRequest));
    }

}
