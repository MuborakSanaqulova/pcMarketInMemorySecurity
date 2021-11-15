package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Basket;
import uz.pdp.vazifa.entity.Order;
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

    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public Order add(OrderDto orderDto) {
        Basket basketServiceById = basketService.getById(orderDto.getBasketId());
        if (basketServiceById==null)
            return null;

        Order order = new Order();
        order.setPrice(orderDto.getPrice());
        order.setBasket(basketServiceById);
        Order save = orderRepository.save(order);

        basketService.add(new BasketDto(basketServiceById.getUser().getId()));

        return save;
    }

}
