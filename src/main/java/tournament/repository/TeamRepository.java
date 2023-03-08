package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
