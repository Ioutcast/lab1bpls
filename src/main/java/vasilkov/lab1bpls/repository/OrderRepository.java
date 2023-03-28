package vasilkov.lab1bpls.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import vasilkov.lab1bpls.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @NonNull Optional<Order> findById(@NonNull Integer integer);

}
