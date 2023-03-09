package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.Matchup;

public interface MatchupRepository extends JpaRepository<Matchup, Long> {
}
