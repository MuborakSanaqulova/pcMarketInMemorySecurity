package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.SupplierMessage;
@Repository
public interface SupplierMassageRepository extends JpaRepository<SupplierMessage, Integer> {
}
