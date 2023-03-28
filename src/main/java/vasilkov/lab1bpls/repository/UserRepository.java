package vasilkov.lab1bpls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vasilkov.lab1bpls.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String username);

}
