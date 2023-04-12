package vasilkov.lab1bpls.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov.lab1bpls.entity.User;

import java.util.Optional;

@Repository
@Hidden
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String username);

}
