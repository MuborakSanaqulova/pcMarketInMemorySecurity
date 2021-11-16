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
import uz.pdp.vazifa.entity.Product;
import uz.pdp.vazifa.payload.ProductDto;
import uz.pdp.vazifa.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productService.getAll(pageable);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Product product = productService.getById(id);
        if (product != null)
            return ResponseEntity.status(HttpStatus.OK).body(product);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductDto productDto) {
        Product product = productService.add(productDto);
        return ResponseEntity.status(product != null ? 202 : 409).body(product);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        Product product = productService.edit(id, productDto);
        return ResponseEntity.status(product != null ? 202 : 409).body(product);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = productService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
