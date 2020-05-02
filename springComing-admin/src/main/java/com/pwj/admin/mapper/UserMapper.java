package com.pwj.admin.mapper;

import com.pwj.admin.aop.annotation.ChangeSource;
import org.apache.ibatis.annotations.Mapper;
import com.pwj.admin.model.entity.User;

@Mapper
public interface UserMapper {

    //接口的方法默认为public，否则无法被继承，这样无意义
    @ChangeSource(value = "dataSource2")
    User selectUserByUsername(String username);

    void insertUserByUsernameAndPassword(User user);
}
