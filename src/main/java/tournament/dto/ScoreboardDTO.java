package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreboardDTO {
    private TeamDTO teamDTO;
    private Long wins;
    private Long losses;
}
