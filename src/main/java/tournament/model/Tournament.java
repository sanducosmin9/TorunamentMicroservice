package tournament.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "TOURNAMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tournament {
    @Id
    @SequenceGenerator(name = "TOURNAMENT_ID_SEQUENCE", sequenceName = "TOURNAMENT_ID_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOURNAMENT_ID_SEQUENCE")
    @Column(name = "TOURNAMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private TournamentUser owner;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SIZE")
    private int size;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "GAME")
    private String game;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    @OrderBy("matchupNumber")
    private List<Matchup> matchups;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<Team> teams;

    @OneToOne
    @JoinColumn
    @Nullable
    private Team winner;
}
