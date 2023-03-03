package tournament.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "TOURNAMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;
}
