package tournament.service;

import tournament.dto.TournamentUserDto;

public interface TournamentUserService {
    TournamentUserDto createUser(TournamentUserDto tournamentUserDto);
    TournamentUserDto getUser(Long id);
}
