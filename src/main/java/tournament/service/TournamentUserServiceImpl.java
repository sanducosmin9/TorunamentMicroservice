package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    @Override
    public TournamentUserDto createUser(TournamentUserDto tournamentUserDto) {
        TournamentUser tournamentUser = mapToTournamentUser(tournamentUserDto);
        tournamentUser.setPassword(passwordEncoder.encode(tournamentUserDto.getPassword()));
        TournamentUser persistedTournamentUser = tournamentUserRepository.save(tournamentUser);
        return mapToTournamentUserDto(persistedTournamentUser);
    }

    @Override
    public TournamentUserDto getUser(Long id) {
        var tournamentUser = tournamentUserRepository.findById(id);
        if(tournamentUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " was not found");
        }
        return mapToTournamentUserDto(tournamentUser.get());
    }
}
