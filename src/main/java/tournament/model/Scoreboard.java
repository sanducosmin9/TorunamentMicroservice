package tournament.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "SCOREBOARD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Scoreboard {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Tournament tournament;

    @OneToOne
    @JoinColumn
    private Team team;

    @Column(name = "WINS")
    private Long wins;

    @Column(name = "LOSSES")
    private Long losses;

}
