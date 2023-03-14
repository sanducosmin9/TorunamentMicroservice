package tournament.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.dto.MatchupDTO;
import tournament.dto.TeamDTO;
import tournament.dto.TournamentDTO;
import tournament.model.Tournament;

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
                tournament.getOwner().getUsername(),
                tournament.getName(),
                tournament.getCreationDate(),
                tournament.getGame(),
                tournament.getMatchups().stream()
                        .map(matchupDTOMapper)
                        .collect(Collectors.groupingBy(MatchupDTO::getRound)),
                tournament.getTeams().stream()
                        .map(it -> new TeamDTO(it.getId(), it.getName()))
                        .collect(Collectors.toList()),
                tournament.getWinner() == null ? null
                        : new TeamDTO(tournament.getWinner().getId(), tournament.getWinner().getName())
        );
    }
}
