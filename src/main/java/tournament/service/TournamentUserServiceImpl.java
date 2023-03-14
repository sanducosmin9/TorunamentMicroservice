package tournament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tournament.model.TournamentUser;
import tournament.repository.TournamentUserRepository;

@Service
@RequiredArgsConstructor
public class TournamentUserServiceImpl implements TournamentUserService {

    private final TournamentUserRepository tournamentUserRepository;

    @Override
    public TournamentUser getUserDetails(String username) {
        return tournamentUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " was not found"));
    }
}
