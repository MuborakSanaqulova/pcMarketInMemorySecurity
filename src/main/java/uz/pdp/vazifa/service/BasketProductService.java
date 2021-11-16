package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Basket;
import uz.pdp.vazifa.entity.BasketProduct;
import uz.pdp.vazifa.entity.Brand;
import uz.pdp.vazifa.entity.Product;
import uz.pdp.vazifa.payload.BasketProductDto;
import uz.pdp.vazifa.payload.BrandDto;
import uz.pdp.vazifa.repository.BasketProductRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BasketProductService {

    @Autowired
    BasketProductRepository basketProductRepository;

    @Autowired
    ProductService productService;

    @Autowired
    BasketService basketService;

    public Page<BasketProduct> getAll(Pageable pageable) {
        return basketProductRepository.findAll(pageable);
    }

    public BasketProduct getById(Integer id) {
        Optional<BasketProduct> optionalBasketProduct = basketProductRepository.findById(id);
        return optionalBasketProduct.orElse(null);
    }

    public BasketProduct add(BasketProductDto basketProductDto) {
        Product productServiceById = productService.getById(basketProductDto.getProductId());
        if (productServiceById == null)
            return null;

        Basket basketServiceById = basketService.getById(basketProductDto.getBasketId());
        if (basketServiceById == null)
            return null;

        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setCount(basketProductDto.getCount());
        basketProduct.setBasket(basketServiceById);
        basketProduct.setProduct(productServiceById);
        return basketProductRepository.save(basketProduct);
    }

    public BasketProduct edit(Integer id, BasketProductDto basketProductDto) {
        Optional<BasketProduct> optionalBasketProduct = basketProductRepository.findById(id);
        if (!optionalBasketProduct.isPresent())
            return null;

        Product productServiceById = productService.getById(basketProductDto.getProductId());
        if (productServiceById == null)
            return null;

        Basket basketServiceById = basketService.getById(basketProductDto.getBasketId());
        if (basketServiceById == null)
            return null;

        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setId(id);
        basketProduct.setCount(basketProductDto.getCount());
        basketProduct.setBasket(basketServiceById);
        basketProduct.setProduct(productServiceById);
        return basketProductRepository.save(basketProduct);
    }

    public boolean delete(Integer id) {
        try {
            basketProductRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteByBasketId(Integer basketId){
        basketProductRepository.deleteAllByBasketId(basketId);
    }

}
