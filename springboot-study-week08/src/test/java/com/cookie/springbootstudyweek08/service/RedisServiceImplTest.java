package com.cookie.springbootstudyweek08.service;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * RedisServiceTest.java
 *
 * @author mqxu
 */
@SpringBootTest
@Slf4j
public class RedisServiceImplTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试RedisTemplate模板的基本操作
     */
    @Test
    public void testRedisTemplate() throws Exception {
        // 测试字符串操作
        redisTemplate.opsForValue().set("code:13900003333", "0000");
        String code = Objects.requireNonNull(redisTemplate.opsForValue().get("code:13900003333")).toString();
        log.info("13900003333验证码测试结果：{}", code);

        // 测试对象操作
        Address address = new Address();
        address.setCity("南京市");
        address.setStreet("栖霞区羊山北路1号");
        address.setZipCode("210000");

        User user = new User();
        user.setName("张三");
        user.setAge(22);
        user.setEmail("zhangsan@qq.com");
        user.setAddress(address);
        redisTemplate.opsForValue().set("user:001", user);

        Object userObj = redisTemplate.opsForValue().get("user:001");
        // 反序列化
        User user2 = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        log.info("user:001测试结果：{}", user2);
    }

    // ---------- 以下为测试中用到的实体类（补充定义） ----------
    @lombok.Data
    static class User {
        private String name;
        private Integer age;
        private String email;
        private Address address;
    }

    @lombok.Data
    static class Address {
        private String city;
        private String street;
        private String zipCode;
    }
}