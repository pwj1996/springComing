package com.pwj.admin.mapper;

import com.pwj.admin.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

//思考为什么要使用spring
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//使用@MybatisTest，如果不加注解@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)，那么mybatis使用的是内存数据库，并不是真实的tddl的数据库，会报表不存在的错，
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    @Qualifier("applicationContextProvider")
//    private ApplicationContextProvider contextProvider;

    //测试一级缓存
    //仅执行一次sql语句
    @Test
    public void OneCacheTest() {
        //DataSource source = contextProvider.getBean(DataSource.class);
        System.out.println("datasource is " + dataSource);
        User user = new User("panweijia", "123456");
        userMapper.insertUserByUsernameAndPassword(user);
    }
}