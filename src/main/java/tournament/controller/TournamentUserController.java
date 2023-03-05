package tournament.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{id}")
    public ResponseEntity<TournamentUserDto> getTournamentUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(tournamentUserService.getUser(id));
    }

}
