package tournament.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournament.dto.TeamDTO;
import tournament.dto.TournamentDTO;
import tournament.model.Tournament;
import tournament.service.TournamentService;

import java.util.List;

@RestController
@RequestMapping("/tournament")
@RequiredArgsConstructor
@CrossOrigin
public class TournamentController {

    private final TournamentService tournamentService;

    // post create tournament -> returns id

    // put update tournament by id

    // get tournament by id

    // get tournament by owner id

    @PostMapping
    public ResponseEntity<Long> createTournament(
            @RequestBody TournamentDTO tournamentDto
    ) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentDto));
    }

    @GetMapping
    public ResponseEntity<Tournament> something() {
        return ResponseEntity.ok(tournamentService.createMockTournament());
    }

}
