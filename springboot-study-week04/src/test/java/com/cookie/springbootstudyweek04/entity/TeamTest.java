package com.cookie.springbootstudyweek04.entity;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Team实体类配置注入+校验规则单元测试
 */
@SpringBootTest
@Slf4j
class TeamTest {

    // 从Spring容器注入配置好的Team Bean
    @Resource
    private Team team;

    @Test
    void testTeam() {
        // 1. 打印注入的配置，验证yml是否成功注入
        log.info("团队配置信息：{}", team);

        // 2. 断言校验：验证所有字段是否符合我们定义的校验规则
        // 验证姓名长度2-10位
        assertTrue(team.getLeader().length() >= 2 && team.getLeader().length() <= 10,
                "负责人姓名长度不符合规则");
        // 验证年龄30-60岁
        assertTrue(team.getAge() >= 30 && team.getAge() <= 60,
                "年龄不符合规则");
        // 验证邮箱格式（内置正则匹配）
        assertTrue(team.getEmail().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
                "邮箱格式不符合规则");
        // 验证手机号11位纯数字
        assertTrue(team.getPhone().matches("^[0-9]{11}$"),
                "手机号格式不符合规则");
        // 验证创建时间是过去的时间
        assertTrue(team.getCreateTime().isBefore(LocalDate.now()),
                "创建时间必须是过去的时间");

        log.info("所有校验规则断言通过！");
    }
}