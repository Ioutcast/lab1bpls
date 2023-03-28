package vasilkov.lab1bpls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vasilkov.lab1bpls.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false)
                  """)
    List<Token> findAllValidTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}