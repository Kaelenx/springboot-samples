package com.cookie.springbootstudyweek08.sms;

/**
 * 短信验证码在 Redis 中的 key 规则。
 */
public final class SmsRedisKey {

    public static final String PREFIX = "week08:sms:code:";

    private SmsRedisKey() {
    }

    public static String ofPhone(String phone) {
        return PREFIX + phone;
    }
}
