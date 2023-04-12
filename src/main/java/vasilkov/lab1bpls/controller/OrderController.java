package vasilkov.lab1bpls.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vasilkov.lab1bpls.entity.Order;
import vasilkov.lab1bpls.model.OrderRequest;
import vasilkov.lab1bpls.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Bad request")
})
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody OrderRequest orderRequestModel) {
        return ResponseEntity.ok(orderService.save(orderRequestModel));
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<?> addNewOrderWithId(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findAndSave(id));
    }

    @GetMapping()
    public ResponseEntity<?> getUserOrder(@RequestParam(required = false) Map<String, String> values) {

        if (values.isEmpty())
            return orderService.findOrdersList();

        List<Order> orders = orderService.findAllOrdersBySpecification(values);
        System.out.println(orders);
        return ResponseEntity.ok(orders);

    }
}
