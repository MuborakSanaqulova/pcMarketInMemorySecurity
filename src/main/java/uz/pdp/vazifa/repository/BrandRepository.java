package uz.pdp.vazifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.vazifa.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
