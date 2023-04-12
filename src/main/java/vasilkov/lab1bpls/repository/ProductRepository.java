package vasilkov.lab1bpls.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov.lab1bpls.entity.Product;

import java.util.Optional;

@Repository
@Hidden
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByArticle(Integer article);
}
