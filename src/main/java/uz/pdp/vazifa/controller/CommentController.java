package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa.entity.Comment;
import uz.pdp.vazifa.entity.SupplierMessage;
import uz.pdp.vazifa.payload.CommentDto;
import uz.pdp.vazifa.payload.SupplierMassageDto;
import uz.pdp.vazifa.service.CommentService;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Comment> comments = commentService.getAll(pageable);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        if (comment != null)
            return ResponseEntity.status(HttpStatus.OK).body(comment);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentDto commentDto) {
        Comment comment = commentService.add(commentDto);
        return ResponseEntity.status(comment != null ? 202 : 409).body(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.edit(id, commentDto);
        return ResponseEntity.status(comment != null ? 202 : 409).body(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = commentService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
