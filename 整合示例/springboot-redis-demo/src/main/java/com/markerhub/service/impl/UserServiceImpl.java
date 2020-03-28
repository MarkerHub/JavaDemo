package com.markerhub.service.impl;

import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    @Cacheable(cacheNames = "cache_user", key="'user_' + #id")
    public User getById(long id) {

        log.info("进来查库了--------->{}", id);

        User user = new User();
        user.setId(1L);
        user.setUsername("MarkerHub" + id);
        return user;
    }

    @Override
    @CacheEvict(cacheNames = "cache_user", key = "'user_' + #user.id")
    public void update(User user) {

        // 更新逻辑...
    }


}
