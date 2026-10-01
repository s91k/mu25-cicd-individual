package s91k.github.io.bookstore_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.dto.OrderRequest;
import s91k.github.io.bookstore_api.dto.OrderResponse;
import s91k.github.io.bookstore_api.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id){
        return ResponseEntity.ok(OrderResponse.toDto(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest or){
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.toDto(orderService.create(or)));
    }

}
