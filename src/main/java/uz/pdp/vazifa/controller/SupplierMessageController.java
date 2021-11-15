package uz.pdp.vazifa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.vazifa.entity.SupplierMessage;
import uz.pdp.vazifa.payload.SupplierMassageDto;
import uz.pdp.vazifa.service.SupplierMessageService;

@RestController
@RequestMapping("api/supplierMessage")
public class SupplierMessageController {

    @Autowired
    SupplierMessageService messageService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<SupplierMessage> supplierMessages = messageService.getAll(pageable);
        return ResponseEntity.ok(supplierMessages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        SupplierMessage message = messageService.getById(id);
        if (message != null)
            return ResponseEntity.status(HttpStatus.OK).body(message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody SupplierMassageDto supplierMassageDto) {
        SupplierMessage supplierMessage = messageService.add(supplierMassageDto);
        return ResponseEntity.status(supplierMessage != null ? 202 : 409).body(supplierMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody SupplierMassageDto supplierMassageDto) {
        SupplierMessage supplierMessage = messageService.edit(id, supplierMassageDto);
        return ResponseEntity.status(supplierMessage != null ? 202 : 409).body(supplierMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean delete = messageService.delete(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
