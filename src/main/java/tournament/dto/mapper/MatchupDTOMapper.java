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
                matchup.getId(),
                matchup.getTeam1() == null ? null
                        : new TeamDTO(matchup.getTeam1().getId(), matchup.getTeam1().getName()),
                matchup.getTeam2() == null ? null
                        : new TeamDTO(matchup.getTeam2().getId(), matchup.getTeam2().getName()),
                matchup.getWinner() == null ? null
                        : new TeamDTO(matchup.getWinner().getId(), matchup.getWinner().getName()),
                matchup.getWinner() == null ? null
                        : new TeamDTO(matchup.getLoser().getId(), matchup.getLoser().getName()),
                matchup.isHasEnded()
        );
    }
}
