package tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tournament.model.Token;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(
        "select t from Token t join TournamentUser u on t.user.id = u.id " +
                "where u.id = :id and (t.expired = false or t.revoked = false)"
    )
    List<Token> findAllValidTokenByUser(Long id);
}
