package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.TournamentUser;

public interface TournamentUserRepository extends JpaRepository<TournamentUser, Long> {
}
