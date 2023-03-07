package tournament.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tournament.model.Tournament;
import tournament.service.TournamentService;

@RestController
@RequestMapping("/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping()
    public ResponseEntity<Tournament> something() {
        return ResponseEntity.ok(tournamentService.createTournament());
    }

}
