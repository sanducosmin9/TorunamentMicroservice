package tournament.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Matchup {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn
    private Team winner;

    @OneToOne
    @JoinColumn
    private Team loser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Tournament tournament;

    private boolean hasEnded;
}
