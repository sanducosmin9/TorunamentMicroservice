package tournament.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.dto.TeamDTO;
import tournament.dto.TournamentDTO;
import tournament.dto.TournamentUserDTO;
import tournament.model.Tournament;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentDTOMapper implements Function<Tournament, TournamentDTO> {

    private final MatchupDTOMapper matchupDTOMapper;

    @Override
    public TournamentDTO apply(Tournament tournament) {
        return new TournamentDTO(
                tournament.getId(),
                new TournamentUserDTO(tournament.getOwner().getUsername(), new ArrayList<>()),
                tournament.getName(),
                tournament.getCreationDate(),
                tournament.getMatchups().stream().map(matchupDTOMapper).collect(Collectors.toList()),
                tournament.getTeams().stream()
                        .map(it -> new TeamDTO(it.getId(), it.getName()))
                        .collect(Collectors.toList()),
                tournament.getWinner() == null ? null
                        : new TeamDTO(tournament.getWinner().getId(), tournament.getWinner().getName())
        );
    }
}
