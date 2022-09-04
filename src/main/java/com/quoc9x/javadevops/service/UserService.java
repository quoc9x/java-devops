package com.quoc9x.javadevops.service;

import com.quoc9x.javadevops.entity.User;
import com.quoc9x.javadevops.model.dto.UserDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    public List<UserDto> getListUser();

    public UserDto getUserById(int id);

    public List<UserDto> searchUser(String keyword);
}
