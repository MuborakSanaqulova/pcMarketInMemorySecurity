package uz.pdp.vazifa.payload;

import lombok.Data;
import uz.pdp.vazifa.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

//    private Integer id;

    private String fullName;

    private String login;

    private String password;

    private String email;

//    public UserDto toDto(User user){
//        UserDto dto = new UserDto();
//        dto.setId(user.getId());
//        dto.setEmail(user.getEmail());
//        dto.setLogin(user.getLogin());
//        dto.setFullName(user.getFullName());
//        return dto;
//    }
//
//    public User toEntity(UserDto userDto){
//        User user = new User();
//        user.setEmail(userDto.getEmail());
//        user.setLogin(userDto.getLogin());
//        user.setFullName(userDto.getFullName());
//        user.setPassword(userDto.getPassword());
//        return user;
//    }
//
//    public List<UserDto> toDtoList(List<User> userList){
//        List<UserDto> dtoList = new ArrayList<>();
//        for (User user: userList) {
//            dtoList.add(toDto(user));
//        }
//        return dtoList;
//    }
}
