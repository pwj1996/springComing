package com.pwj.upload.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

public class RedisDao {

    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisDao(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void redisSetTest(RedisTemplate redisTemplate) {
        SetOperations<String, String> set = redisTemplate.opsForSet();

        set.add("set2","22");

        set.add("set2","33");

        set.add("set1","44");

        Set<String> resultSet =redisTemplate.opsForSet().members("set1");

        System.out.println("resultSet:"+resultSet);
    }



}

