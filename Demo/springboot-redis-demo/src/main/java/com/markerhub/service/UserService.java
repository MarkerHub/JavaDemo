package com.markerhub.service;

import com.markerhub.entity.User;

public interface UserService {

    User getById(long id);

    void update(User user);

}
