package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tournament.model.TournamentUser;

import java.util.Optional;

public interface TournamentUserRepository extends JpaRepository<TournamentUser, Long> {

    Optional<TournamentUser> findByUsername(String username);

}
