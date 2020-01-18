package com.markerhub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-01-06
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test1")
    public Object select (HttpServletRequest req) {

        // 查找
        User user0 = userService.getById(1);
        User user1 = userService.getOne(new QueryWrapper<User>()
                .eq("username", "markerhub"));
        System.out.println(user1.toString());

        int pn = ServletRequestUtils.getIntParameter(req, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 2);
        Page p = new Page(pn, size);
        IPage page0 = userService.page(p, new QueryWrapper<User>()
                .isNotNull("id")
                .orderByDesc("created"));
        System.out.println(page0.toString());

        // 嵌套查询
//        IPage page1 = userService.page(p, new QueryWrapper<User>()
//                .inSql("id", "SELECT user_id FROM user_collection where post_id = 1")
//        );
//
//        // 增删改都是差不多，直接调用api
//        user0.setUsername("markerhub0");
//        userService.saveOrUpdate(user0);
//        userService.remove(new QueryWrapper<User>().eq("username", "markerhub0"));

        return "ok";
    }



}
