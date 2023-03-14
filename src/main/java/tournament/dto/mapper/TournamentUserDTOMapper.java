package tournament.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.dto.TournamentUserDTO;
import tournament.model.TournamentUser;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentUserDTOMapper implements Function<TournamentUser, TournamentUserDTO> {

    private final TournamentDTOMapper tournamentDTOMapper;

    @Override
    public TournamentUserDTO apply(TournamentUser tournamentUser) {
        return new TournamentUserDTO(
                tournamentUser.getUsername(),
                tournamentUser.getEmail(),
                tournamentUser.getFirstName(),
                tournamentUser.getLastName(),
                tournamentUser.getTournaments().stream().map(tournamentDTOMapper).collect(Collectors.toList())
        );
    }
}
