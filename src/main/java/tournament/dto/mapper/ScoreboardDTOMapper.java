package tournament.dto.mapper;

import org.springframework.stereotype.Service;
import tournament.dto.ScoreboardDTO;
import tournament.dto.TeamDTO;
import tournament.model.Scoreboard;

import java.util.function.Function;

@Service
public class ScoreboardDTOMapper implements Function<Scoreboard, ScoreboardDTO> {
    @Override
    public ScoreboardDTO apply(Scoreboard scoreboard) {
        return new ScoreboardDTO(
                new TeamDTO(scoreboard.getTeam().getId(), scoreboard.getTeam().getName()),
                scoreboard.getWins(),
                scoreboard.getLosses()
        );
    }
}
