package com.quoc9x.javadevops.model.mapper;

import com.quoc9x.javadevops.entity.User;
import com.quoc9x.javadevops.model.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setName(user.getName());
        tmp.setEmail(user.getEmail());
        tmp.setPhone(user.getPhone());
        tmp.setAvatar(user.getAvatar());

        return tmp;
    }
}
