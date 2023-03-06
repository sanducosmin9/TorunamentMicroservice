package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
