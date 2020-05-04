package com.pwj.upload.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDaoTest {

    @Resource(name="redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    // inject the template as ListOperations
    @Resource(name="redisTemplate")
    private SetOperations<String, String> settOps;

    @Test
    public void redisSet() {
        SetOperations<String, String> set = redisTemplate.opsForSet();

        set.add("set1","22");

        set.add("set1","33");

        set.add("set1","44");

        Set<String> resultSet =redisTemplate.opsForSet().members("set1");

        System.out.println("resultSet:"+resultSet);
    }

}