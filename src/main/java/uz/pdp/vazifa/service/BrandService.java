package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Brand;
import uz.pdp.vazifa.payload.BrandDto;
import uz.pdp.vazifa.repository.BrandRepository;

import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public Page<Brand> getAll(Pageable pageable) {
       return brandRepository.findAll(pageable);
    }

    public Brand getById(Integer id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent())
            return optionalBrand.get();
        return null;
    }

    public Brand add(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.getName());
        brandRepository.save(brand);
        return brand;
    }

    public Brand edit(Integer id, BrandDto brandDto) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()) {
            Brand brand = new Brand();
            brand.setId(id);
            brand.setName(brandDto.getName());
            brandRepository.save(brand);
            return brand;
        }
        return null;
    }

    public boolean delete(Integer id) {
        try {
            brandRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
