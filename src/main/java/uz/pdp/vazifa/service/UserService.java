package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Brand;
import uz.pdp.vazifa.entity.User;
import uz.pdp.vazifa.payload.BasketDto;
import uz.pdp.vazifa.payload.UserDto;
import uz.pdp.vazifa.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BasketService basketService;

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User add(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        User save = userRepository.save(user);
        basketService.add(new BasketDto(save.getId()));
        return save;
    }

    public User edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = new User();
            user.setId(id);
            user.setFullName(userDto.getFullName());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setLogin(userDto.getLogin());
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public boolean delete(Integer id) {
        try {
            userRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
