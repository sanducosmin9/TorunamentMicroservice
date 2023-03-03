package tournament.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import tournament.model.Tournament;
import tournament.model.UserRole;

import java.util.List;

@Data
@Builder
public class TournamentUserDto {
    private Long id;
    private String username;
    // subject to change with security
    private String password;
    private UserRole role;
    private List<Tournament> tournaments;
}
