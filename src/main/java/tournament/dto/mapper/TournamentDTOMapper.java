package tournament.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tournament.dto.TeamDTO;
import tournament.dto.TournamentDTO;
import tournament.dto.TournamentUserDTO;
import tournament.model.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentDTOMapper implements Function<Tournament, TournamentDTO> {

    private final MatchupDTOMapper matchupDTOMapper;

    @Override
    public TournamentDTO apply(Tournament tournament) {
        return new TournamentDTO(
                new TournamentUserDTO(tournament.getOwner().getUsername(), new ArrayList<>()),
                tournament.getName(),
                tournament.getSize(),
                tournament.getCreationDate(),
                tournament.getMatchups().stream().map(matchupDTOMapper).collect(Collectors.toList()),
                tournament.getTeams().stream().map(it -> new TeamDTO(it.getName())).collect(Collectors.toList())
        );
    }
}
