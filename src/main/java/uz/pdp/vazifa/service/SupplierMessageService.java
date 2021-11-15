package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.SupplierMessage;
import uz.pdp.vazifa.entity.User;
import uz.pdp.vazifa.payload.SupplierMassageDto;
import uz.pdp.vazifa.repository.SupplierMassageRepository;

import java.util.Optional;

@Service
public class SupplierMessageService {

    @Autowired
    SupplierMassageRepository supplierMassageRepository;

    @Autowired
    UserService userService;

    public Page<SupplierMessage> getAll(Pageable pageable) {
        return supplierMassageRepository.findAll(pageable);
    }

    public SupplierMessage getById(Integer id) {
        Optional<SupplierMessage> optionalSupplierMessage = supplierMassageRepository.findById(id);
        return optionalSupplierMessage.orElse(null);
    }

    public SupplierMessage add(SupplierMassageDto supplierMassageDto) {
        User byId = userService.getById(supplierMassageDto.getUserId());
        if (byId==null)
            return null;
        SupplierMessage supplierMessage = new SupplierMessage();
        supplierMessage.setSupplierMessage(supplierMassageDto.getSupplierMessage());
        supplierMessage.setUser(byId);
        supplierMassageRepository.save(supplierMessage);
        return supplierMessage;
    }

    public SupplierMessage edit(Integer id, SupplierMassageDto supplierMassageDto) {
        User byId = userService.getById(supplierMassageDto.getUserId());
        if (byId==null)
            return null;
        Optional<SupplierMessage> optionalSupplierMessage = supplierMassageRepository.findById(id);
        if (optionalSupplierMessage.isPresent()) {
            SupplierMessage supplierMessage = new SupplierMessage();
            supplierMessage.setId(id);
            supplierMessage.setSupplierMessage(supplierMassageDto.getSupplierMessage());
            supplierMessage.setUser(byId);
            supplierMassageRepository.save(supplierMessage);
            return supplierMessage;
        }
        return null;
    }

    public boolean delete(Integer id) {
        try {
            supplierMassageRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
