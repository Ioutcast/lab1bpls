package vasilkov.lab1bpls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vasilkov.lab1bpls.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByArticle(Integer article);
}
