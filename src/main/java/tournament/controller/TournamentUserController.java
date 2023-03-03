package tournament.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournament.dto.TournamentUserDto;
import tournament.service.TournamentUserService;

@RestController
@RequestMapping("/tournament/user")
@RequiredArgsConstructor
public class TournamentUserController {

    private final TournamentUserService tournamentUserService;

    @PostMapping
    public ResponseEntity<TournamentUserDto> createTournamentUser(
            @RequestBody TournamentUserDto tournamentUserDto
    ) {
        return ResponseEntity.ok(tournamentUserService.createUser(tournamentUserDto));
    }

    @GetMapping
    public ResponseEntity<TournamentUserDto> getTournamentUser(
            @RequestBody TournamentUserDto tournamentUserDto
    ) {
        return ResponseEntity.ok(tournamentUserService.getUser(tournamentUserDto));
    }
}
