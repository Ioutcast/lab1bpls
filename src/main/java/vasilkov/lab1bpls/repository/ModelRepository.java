package vasilkov.lab1bpls.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov.lab1bpls.entity.Model;

import java.util.Optional;

@Repository
@Hidden
public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findModelByName(String name);
}
