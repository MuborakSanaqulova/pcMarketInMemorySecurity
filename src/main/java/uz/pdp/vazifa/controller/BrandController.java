package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa.entity.Brand;
import uz.pdp.vazifa.payload.BrandDto;
import uz.pdp.vazifa.service.BrandService;

@RestController
@RequestMapping("api/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Brand> brands = brandService.getAll(pageable);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Brand brand = brandService.getById(id);
        if (brand != null)
            return ResponseEntity.status(HttpStatus.OK).body(brand);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BrandDto brandDto) {
        Brand brand = brandService.add(brandDto);
        return ResponseEntity.status(201).body(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        Brand brand = brandService.edit(id, brandDto);
        return ResponseEntity.status(brand != null ? 202 : 409).body(brand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = brandService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
