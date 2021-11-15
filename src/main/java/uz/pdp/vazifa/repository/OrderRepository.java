package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
