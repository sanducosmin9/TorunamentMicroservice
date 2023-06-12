package tournament.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
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
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Tournament tournament;

    @OneToOne
    @Nullable
    @JoinColumn
    private Matchup activeMatchup;

    @OneToOne
    @Nullable
    @JoinColumn(name = "scoreboard_id")
    private Scoreboard scoreboard;
}
