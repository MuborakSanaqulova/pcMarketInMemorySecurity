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
import uz.pdp.vazifa.entity.BasketProduct;
import uz.pdp.vazifa.payload.BasketProductDto;
import uz.pdp.vazifa.service.BasketProductService;

@RestController
@RequestMapping("api/basketProduct")
public class BasketProductController {

    @Autowired
    BasketProductService basketProductService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BasketProduct> basketProducts = basketProductService.getAll(pageable);
        return ResponseEntity.ok(basketProducts);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        BasketProduct basketProduct = basketProductService.getById(id);
        if (basketProduct != null)
            return ResponseEntity.status(HttpStatus.OK).body(basketProduct);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody BasketProductDto basketProductDto) {
        BasketProduct basketProduct = basketProductService.add(basketProductDto);
        return ResponseEntity.status(201).body(basketProduct);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody BasketProductDto basketProductDto) {
        BasketProduct basketProduct = basketProductService.edit(id, basketProductDto);
        return ResponseEntity.status(basketProduct != null ? 202 : 409).body(basketProduct);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = basketProductService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
