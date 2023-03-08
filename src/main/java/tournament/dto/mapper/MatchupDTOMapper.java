package tournament.dto.mapper;

import org.springframework.stereotype.Service;
import tournament.dto.MatchupDTO;
import tournament.dto.TeamDTO;
import tournament.model.Matchup;

import java.util.function.Function;

@Service
public class MatchupDTOMapper implements Function<Matchup, MatchupDTO> {
    @Override
    public MatchupDTO apply(Matchup matchup) {
        return new MatchupDTO(
                new TeamDTO(matchup.getTeam1().getName()),
                new TeamDTO(matchup.getTeam2().getName()),
                new TeamDTO(matchup.getWinner().getName()),
                new TeamDTO(matchup.getLoser().getName()),
                matchup.isHasEnded()
        );
    }
}
