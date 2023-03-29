package vasilkov.lab1bpls.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vasilkov.lab1bpls.entity.Order;
import vasilkov.lab1bpls.model.OrderRequest;
import vasilkov.lab1bpls.repository.OrderRepository;
import vasilkov.lab1bpls.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    @PutMapping("/add")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody OrderRequest orderRequestModel) {
        return ResponseEntity.ok(orderService.save(orderRequestModel));
    }

    @PutMapping("/add-to-order/{id}")
    public ResponseEntity<?> addNewOrder1(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findAndSave(id));
    }


    @GetMapping(value = "/my")
    public ResponseEntity<?> getUserOrder() {
        //todo перенести в сервис
        Optional<List<Order>> ordersList = orderRepository.findAllByUserEmail(getCurrentEmail());

        if (ordersList.isEmpty())
            return ResponseEntity.ok("You haven't any orders yet");

        return ResponseEntity.ok(ordersList);
    }

    private String getCurrentEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> searchOrdersWithParam(@RequestParam Map<String, String> values) {
        List<Order> orders = orderService.findAllOrdersBySpecification(values);
        return ResponseEntity.ok(orders);
    }
}
