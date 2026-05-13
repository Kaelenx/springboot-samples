package com.cookie.springbootstudyweek08.sms;

import com.cookie.springbootstudyweek08.sms.dto.SendCodeResponse;
import com.cookie.springbootstudyweek08.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 短信验证码：使用 Redis String 存验证码，并设置过期时间。
 * 校验成功后通过 Lua 脚本原子删除，避免验证码被重复消费。
 */
@Service
@RequiredArgsConstructor
public class SmsVerifyCodeService {

    public static final int DEFAULT_TTL_SECONDS = 5 * 60;

    private final RedisUtil redisUtil;

    public SendCodeResponse sendCode(String phone) {
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000));
        String key = SmsRedisKey.ofPhone(phone);
        redisUtil.set(key, code, DEFAULT_TTL_SECONDS, TimeUnit.SECONDS);
        return new SendCodeResponse(phone, DEFAULT_TTL_SECONDS, code);
    }

    public boolean validateCode(String phone, String input) {
        String key = SmsRedisKey.ofPhone(phone);
        Object raw = redisUtil.get(key);
        if (raw == null) {
            return false;
        }
        String cached = Objects.toString(raw, "");
        if (cached.isEmpty() || !cached.equals(input)) {
            return false;
        }
        redisUtil.delete(key);
        return true;
    }
}
