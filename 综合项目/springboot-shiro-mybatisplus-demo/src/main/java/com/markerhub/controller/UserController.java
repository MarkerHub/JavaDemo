package com.markerhub.controller;


import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-04-20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * mybatis plus 测试
     */
    @GetMapping(value = "{id}")
    public Object test(@PathVariable("id") Long id) {
        return userService.getById(1L);
    }


    /**
     * 实体校验演示
     */
    @ResponseBody
    @PostMapping("/submit")
    public Result submit(User user) {
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if (validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }

        return Result.succ(user);
    }
}
