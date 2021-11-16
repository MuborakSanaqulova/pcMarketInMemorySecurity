package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Basket;
import uz.pdp.vazifa.entity.Order;
import uz.pdp.vazifa.entity.User;
import uz.pdp.vazifa.payload.BasketDto;
import uz.pdp.vazifa.payload.OrderDto;
import uz.pdp.vazifa.repository.OrderRepository;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BasketService basketService;

    @Autowired
    UserService userService;

    @Autowired
    BasketProductService basketProductService;

    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public Order add(OrderDto orderDto) {
        User userServiceById = userService.getById(orderDto.getUserId());
        if (userServiceById==null)
            return null;

        Double aDouble = orderRepository.wholePrice(orderDto.getUserId());

        Order order = new Order();
        order.setPrice(aDouble);
        order.setUser(userServiceById);
        Order save = orderRepository.save(order);

        Optional<Basket> basket = basketService.getByUserId(orderDto.getUserId());
        basketProductService.deleteByBasketId(basket.get().getId());
        basketService.add(new BasketDto(userServiceById.getId()));

        return save;
    }

}
