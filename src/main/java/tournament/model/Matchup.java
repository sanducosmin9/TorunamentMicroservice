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

    public Matchup(Long id, Tournament tournament, int round, int matchupNumber) {
        this.id = id;
        this.tournament = tournament;
        this.round = round;
        this.matchupNumber = matchupNumber;
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn
    private Team team1;

    @OneToOne
    @JoinColumn
    private Team team2;

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

    private int round;

    private int matchupNumber;
}
