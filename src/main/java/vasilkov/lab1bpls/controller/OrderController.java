package vasilkov.lab1bpls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vasilkov.lab1bpls.model.OrderRequest;
import vasilkov.lab1bpls.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PutMapping("/add")
    public ResponseEntity<?> addNewAdvertisement(@Valid @RequestBody OrderRequest orderRequestModel) {
        return ResponseEntity.ok(orderService.save(orderRequestModel));
    }
}
