package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.vazifa.common.ApiResponse;
import uz.pdp.vazifa.entity.Attachment;
import uz.pdp.vazifa.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<Page<Attachment>> getInfo(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Attachment> all = attachmentService.getAttachments(pageable);
        return ResponseEntity.ok(all);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        ApiResponse one = attachmentService.findOne(id, response);
        if (one.getSuccess())
            return ResponseEntity.ok(one);
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @PostMapping
    public HttpEntity<ApiResponse> uploadFile(MultipartHttpServletRequest request) throws IOException {
        ApiResponse post = attachmentService.post(request);
        if (post.getSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(post);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse delete = attachmentService.delete(id);
        if (delete.getSuccess())
            return ResponseEntity.status(200).body(delete);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(delete);
    }


}
