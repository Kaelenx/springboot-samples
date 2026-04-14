package com.cookie.springbootstudyweek03.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AppConfigTest {

    @Resource
    private AppConfig appConfig;

    @Test
    void getName() {
        log.info(appConfig.getName());
    }

    @Test
    void getVersion() {
        log.info(appConfig.getVersion());
    }

    @Test
    void getDescription() {
        log.info(appConfig.getDescription());
    }
}
