package vasilkov.lab1bpls.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import vasilkov.lab1bpls.entity.Order;

public class OrderWithCityName implements Specification<Order> {

    private final String city;

    public OrderWithCityName(String city) {
        this.city = city;
    }

    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (city == null) {
            return cb.isTrue(cb.literal(true)); // always true = no filtering
        }

        return cb.equal(root.get("country_of_origin"), this.city);
    }

}