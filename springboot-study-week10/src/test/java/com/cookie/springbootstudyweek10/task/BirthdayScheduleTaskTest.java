package com.cookie.springbootstudyweek10.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BirthdayScheduleTaskTest {

    @Autowired
    private BirthdayScheduleTask birthdayScheduleTask;

    @Test
    public void testSendBirthdayGreeting() {
        birthdayScheduleTask.sendBirthdayGreeting();
        System.out.println("生日祝福邮件测试完成，请查看控制台和邮箱");
    }
}
