package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tournament.model.Matchup;
import tournament.model.Team;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class TournamentDTO {
    private TournamentUserDTO owner;
    private String name;
    private int size;
    private LocalDateTime creationDate;
    private List<MatchupDTO> matchups;
    private List<TeamDTO> teams;
}
