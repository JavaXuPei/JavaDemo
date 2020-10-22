package com.bjy.javademo;

import com.bjy.javademo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavademoApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {

    }



}
