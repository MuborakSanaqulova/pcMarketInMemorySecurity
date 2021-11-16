package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.BasketProduct;

import javax.transaction.Transactional;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {

    @Transactional
    @Modifying
    void deleteAllByBasketId(Integer basket_id);
}
