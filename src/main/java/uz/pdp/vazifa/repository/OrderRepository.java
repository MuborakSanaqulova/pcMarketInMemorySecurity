package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select sum(p.price*bp.count) from product p join basket_product bp on p.id = bp.product_id join basket b on b.id = bp.basket_id where  b.user_id =:id", nativeQuery = true)
    Double wholePrice(Integer id);
}
