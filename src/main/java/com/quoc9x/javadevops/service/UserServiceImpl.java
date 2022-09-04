package com.quoc9x.javadevops.service;

import com.quoc9x.javadevops.entity.User;
import com.quoc9x.javadevops.exception.NotFoundException;
import com.quoc9x.javadevops.model.dto.UserDto;
import com.quoc9x.javadevops.model.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private static ArrayList<User> users = new ArrayList<User>();

    static {
        users.add(new User(1, "Nguyen Van A", "anv@gmail.com", "0965656565", "avatar.img", "123"));
        users.add(new User(2, "Nguyen Van B", "anv@gmail.com", "0965656565", "avatar.img",  "123"));
        users.add(new User(3, "Nguyen Van C", "anv@gmail.com", "0965656565", "avatar.img",  "123"));
        users.add(new User(4, "Nguyen Van D", "anv@gmail.com", "0965656565", "avatar.img", "123"));
    }

    @Override
    public List<UserDto> getListUser() {
        List<UserDto> result = new ArrayList<UserDto>();
        for (User user : users){
            result.add(UserMapper.toUserDto(user));
        }
        return result;
    }

    @Override
    public UserDto getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return UserMapper.toUserDto(user);
            }
        }
        //return null;
        throw new NotFoundException("User không tồn tại trong hệ thống");
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users){
            if (user.getName().contains(keyword)){
                result.add(UserMapper.toUserDto(user));
            }
        }
        return result;
    }

}
