package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class TournamentDTO {
    private Long id;
    private TournamentUserDTO owner;
    private String name;
    private LocalDateTime creationDate;
    private List<MatchupDTO> matchups;
    private List<TeamDTO> teams;
    private TeamDTO winner;
}
