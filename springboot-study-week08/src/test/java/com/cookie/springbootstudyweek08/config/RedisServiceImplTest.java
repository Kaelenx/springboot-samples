package com.cookie.springbootstudyweek08.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;



@SpringBootTest
@Slf4j
public class RedisServiceImplTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试StringRedisTemplate模板的基本操作
     */
    @Test
    public void testConnection() throws Exception {
        // 测试字符串操作
        stringRedisTemplate.opsForValue().set("hello", "world");
        stringRedisTemplate.opsForValue().set("code:13900001111", "1234");
        stringRedisTemplate.opsForValue().set("code:13900002222", "8899");

        // 取值
        String value = stringRedisTemplate.opsForValue().get("hello");
        log.info("Redis 字符串测试结果：{}", value);

        String code = stringRedisTemplate.opsForValue().get("code:13900001111");
        log.info("13900001111验证码测试结果：{}", code);

        String code2 = stringRedisTemplate.opsForValue().get("code:13900002222");
        log.info("13900002222验证码测试结果：{}", code2);
    }
}