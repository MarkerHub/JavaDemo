package com.markerhub.controller;

import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/u/{id}")
    public User index(@PathVariable("id") Long id) {

        User user = new User();
        user.setId(id);
        userService.update(user);

        user = userService.getById(id);
        return user;

    }
}
