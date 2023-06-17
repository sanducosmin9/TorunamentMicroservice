package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TournamentsDTO {
    private List<TournamentDTO> tournamentDTOs;
    private Long numberOfTournaments;
}
