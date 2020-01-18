package com.markerhub.controller;

import com.markerhub.entity.User;
import com.markerhub.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/test")
    public Object test() {

        stringRedisTemplate.opsForValue().set("stringRedisTemplate", "hello，欢迎关注公众号MarkerHub");
        System.out.println("StringRedisTemplate ----> " + stringRedisTemplate.opsForValue().get("stringRedisTemplate"));

        redisTemplate.opsForValue().set("redisTemplate", "hello，欢迎关注公众号MarkerHub");
        System.out.println("RedisTemplate ----> " + redisTemplate.opsForValue().get("redisTemplate"));

        // RedisUtil使用的是RedisTemplate
        System.out.println("RedisTemplate ----> " + redisUtil.get("redisTemplate"));

        User user = new User();
        user.setId(1L);
        user.setUsername("mmm");
        redisUtil.set("user",  user);

        User u = (User) redisUtil.get("user");
        System.out.println(u.toString());

        return "Helle Redis Test";
    }

}
