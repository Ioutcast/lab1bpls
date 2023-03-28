package vasilkov.lab1bpls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vasilkov.lab1bpls.entity.Model;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findModelByName(String name);
}
