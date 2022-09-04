package com.quoc9x.javadevops.controller;

import com.quoc9x.javadevops.entity.User;
import com.quoc9x.javadevops.model.dto.UserDto;
import com.quoc9x.javadevops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Cách viết đối với version mới @RequestMapping("/users")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam(name = "keyword", required = false, defaultValue = "") String name) {
        // Có bao nhiêu query parameter thì có bấy nhiêu @RequestParam + tham số
        List<UserDto> users = userService.searchUser(name);

        return ResponseEntity.ok(users);
    }

    //@RequestMapping(value = "/users", method = RequestMethod.GET) version cũ
    @GetMapping("")
    public ResponseEntity<?> getListUser() {
        List<UserDto> users = userService.getListUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    // Tên param phải giống với tên biến trên URL để SpringBoot thực hiện binding
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        // Annotation @PathVariable giúp lấy ra tham số từ URL
        //System.out.println(id);
        UserDto result = userService.getUserById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser() {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser() {

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser() {

        return null;
    }

}
