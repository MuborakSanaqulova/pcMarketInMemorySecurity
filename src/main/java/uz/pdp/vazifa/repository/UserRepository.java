package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.vazifa.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
