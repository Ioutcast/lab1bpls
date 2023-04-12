package vasilkov.lab1bpls.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov.lab1bpls.entity.Brand;

import java.util.Optional;

@Hidden
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findBrandByName(String brandName);
}

