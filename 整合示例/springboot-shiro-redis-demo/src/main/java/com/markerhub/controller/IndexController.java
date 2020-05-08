package com.markerhub.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    HttpServletRequest req;

    @RequestMapping({"/", "/index"})
    public String index() {
        System.out.println("已登录，正在访问！！");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);

        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                req.setAttribute("errorMess", "用户不存在");
            } else if (e instanceof LockedAccountException) {
                req.setAttribute("errorMess", "用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                req.setAttribute("errorMess", "密码错误");
            } else {
                req.setAttribute("errorMess", "用户认证失败");
            }
            return "/login";
        }
        return "redirect:/";
    }


    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

}
