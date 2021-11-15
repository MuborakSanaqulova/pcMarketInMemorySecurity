package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa.entity.Category;
import uz.pdp.vazifa.entity.Comment;
import uz.pdp.vazifa.payload.CategoryDto;
import uz.pdp.vazifa.payload.CommentDto;
import uz.pdp.vazifa.service.CategoryService;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categories = categoryService.getAll(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        if (category != null)
            return ResponseEntity.status(HttpStatus.OK).body(category);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.add(categoryDto);
        return ResponseEntity.status(category != null ? 202 : 409).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.edit(id, categoryDto);
        return ResponseEntity.status(category != null ? 202 : 409).body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = categoryService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
