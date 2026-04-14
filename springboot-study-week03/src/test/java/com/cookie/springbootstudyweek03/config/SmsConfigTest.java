package com.cookie.springbootstudyweek03.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SmsConfigTest {

    @Resource
    private SmsConfig smsConfig;

    @Test
    void getServerIp() {
        log.info("serverIp: {}", smsConfig.getServerIp());
    }

    @Test
    void getServerPort() {
    }

    @Test
    void getAccountSid() {
    }

    @Test
    void getAccountToken() {
    }

    @Test
    void getAppId() {
    }
}