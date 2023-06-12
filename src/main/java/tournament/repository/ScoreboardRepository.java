package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.Scoreboard;

public interface ScoreboardRepository extends JpaRepository<Scoreboard, Long> {
}
