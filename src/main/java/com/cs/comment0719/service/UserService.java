package com.cs.comment0719.service;

import com.cs.comment0719.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {

    public User getUserByName(String name);
}
