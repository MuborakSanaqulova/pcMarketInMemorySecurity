package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.*;
import uz.pdp.vazifa.payload.ProductDto;
import uz.pdp.vazifa.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Product add(ProductDto productDto) {
        Category categoryServiceById = categoryService.getById(productDto.getCategoryId());
        if (categoryServiceById==null)
            return null;

        Brand brandServiceById = brandService.getById(productDto.getBrandId());
        if (brandServiceById==null)
            return null;

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setBrand(brandServiceById);
        product.setCategory(categoryServiceById);
        productRepository.save(product);
        return product;
    }

    public Product edit(Integer id, ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return null;

        Category categoryServiceById = categoryService.getById(productDto.getCategoryId());
        if (categoryServiceById==null)
            return null;

        Brand brandServiceById = brandService.getById(productDto.getBrandId());
        if (brandServiceById==null)
            return null;

        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setBrand(brandServiceById);
        product.setCategory(categoryServiceById);
        productRepository.save(product);
        return product;

    }

    public boolean delete(Integer id) {
        try {
            productRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void deleteByCategoryId(Integer id) {
        productRepository.deleteByCategoryQuery(id);
    }

}
