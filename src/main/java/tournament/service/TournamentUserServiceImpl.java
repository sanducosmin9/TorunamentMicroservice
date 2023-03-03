package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.dto.TournamentUserDto;
import tournament.exceptions.UserNotFoundException;
import tournament.model.TournamentUser;
import tournament.repository.TournamentUserRepository;

import static tournament.util.TournamentUserMapper.mapToTournamentUser;
import static tournament.util.TournamentUserMapper.mapToTournamentUserDto;

@Service
@RequiredArgsConstructor
public class TournamentUserServiceImpl implements TournamentUserService {

    private final TournamentUserRepository tournamentUserRepository;

    @Override
    public TournamentUserDto createUser(TournamentUserDto tournamentUserDto) {
        TournamentUser tournamentUser = mapToTournamentUser(tournamentUserDto);
        TournamentUser persistedTournamentUser = tournamentUserRepository.save(tournamentUser);
        return mapToTournamentUserDto(persistedTournamentUser);
    }

    @Override
    public TournamentUserDto getUser(TournamentUserDto tournamentUserDto) {
        var tournamentUser = tournamentUserRepository.findById(tournamentUserDto.getId());
        if(tournamentUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + tournamentUserDto.getId() + " was not found");
        }
        return mapToTournamentUserDto(tournamentUser.get());
    }
}
