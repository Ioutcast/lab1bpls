package vasilkov.lab1bpls.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vasilkov.lab1bpls.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @NonNull Optional<Order> findById(@NonNull Integer integer);

    Optional<List<Order>> findAllByUserEmail(String currentEmail);


}
