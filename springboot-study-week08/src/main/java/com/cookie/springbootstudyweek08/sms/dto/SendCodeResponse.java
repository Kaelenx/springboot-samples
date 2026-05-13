package com.cookie.springbootstudyweek08.sms.dto;

/**
 * 联调/演示环境可返回 codePlain，生产环境应删掉该字段。
 */
public record SendCodeResponse(
        String phone,
        int ttlSeconds,
        String codePlain
) {
}
