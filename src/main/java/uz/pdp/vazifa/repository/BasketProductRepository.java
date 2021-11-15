package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.BasketProduct;
@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {
}
