package com.pwj.admin.mapper;

import com.pwj.admin.Application;
import com.pwj.admin.config.DataSourceConfig;
import com.pwj.admin.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.sql.DataSourceDefinition;
import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = Application.class)
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//使用@MybatisTest，如果不加注解@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)，那么mybatis使用的是内存数据库，并不是真实的tddl的数据库，会报表不存在的错，
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    //测试一级缓存
    //仅执行一次sql语句
    @Test
    public void OneCacheTest() {

        User user = new User("panweijia", "123456");
        userMapper.insertUserByUsernameAndPassword(user);
    }
}