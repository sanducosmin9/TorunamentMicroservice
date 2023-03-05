package tournament.service;

import tournament.dto.TournamentUserDto;
import tournament.model.TournamentUser;

public interface TournamentUserService {
    TournamentUserDto createUser(TournamentUserDto tournamentUserDto);
    TournamentUserDto getUser(Long id);
}
