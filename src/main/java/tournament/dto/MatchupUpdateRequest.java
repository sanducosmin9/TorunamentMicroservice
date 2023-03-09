package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchupUpdateRequest {
    private Long tournamentId;
    private Long winnerId;
    private Long matchupId;
}
