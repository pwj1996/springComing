package com.pwj.admin.service.impl;

import com.pwj.admin.mapper.UserMapper;
import com.pwj.admin.model.entity.User;
import com.pwj.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    //构造方法注入
    @Autowired
    AdminServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private UserMapper userMapper;

    public boolean isUser(String username, String password) {
        User user = userMapper.selectUserByUsername(username);

        if (user != null) {
            return password.equals(user.getPassword());
        }

        return false;
    }
}
