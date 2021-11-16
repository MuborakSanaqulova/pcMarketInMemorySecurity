package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa.entity.Order;
import uz.pdp.vazifa.payload.OrderDto;
import uz.pdp.vazifa.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Order> orders = orderService.getAll(pageable);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        if (order != null)
            return ResponseEntity.status(HttpStatus.OK).body(order);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderDto orderDto) {
        Order order = orderService.add(orderDto);
        return ResponseEntity.status(201).body(order);
    }

}
