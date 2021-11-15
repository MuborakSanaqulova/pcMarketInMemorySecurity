package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Basket;
import uz.pdp.vazifa.entity.User;
import uz.pdp.vazifa.payload.BasketDto;
import uz.pdp.vazifa.repository.BasketRepository;

import java.util.Optional;

@Service
public class BasketService {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserService userService;

    public Basket add(BasketDto basketDto){

        User userServiceById = userService.getById(basketDto.getUserId());

        Optional<Basket> byUserId = basketRepository.findByUserId(basketDto.getUserId());
        delete(byUserId.get().getId());

        Basket basket = new Basket();
        basket.setUser(userServiceById);
        return basketRepository.save(basket);
    }

    public Basket getById(Integer id){
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (optionalBasket.isPresent())
            return optionalBasket.get();
        return null;
    }

    public void delete(Integer id){
        basketRepository.deleteById(id);
    }

}
