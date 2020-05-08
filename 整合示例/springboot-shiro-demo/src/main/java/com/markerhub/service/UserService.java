package com.markerhub.service;

import com.markerhub.shiro.AccountProfile;

public interface UserService {

    AccountProfile login(String username, String password);

}
