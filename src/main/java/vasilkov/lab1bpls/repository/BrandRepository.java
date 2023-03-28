package vasilkov.lab1bpls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vasilkov.lab1bpls.entity.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findBrandByName(String brandName);
}

