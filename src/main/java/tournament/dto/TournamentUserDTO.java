package tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TournamentUserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<TournamentDTO> tournaments;
}
