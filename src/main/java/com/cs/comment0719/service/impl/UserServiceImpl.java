package com.cs.comment0719.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.comment0719.entity.User;
import com.cs.comment0719.service.UserService;
import com.cs.comment0719.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getName,name);
        User u = userMapper.selectOne(lqw);
        return u;
    }
}




