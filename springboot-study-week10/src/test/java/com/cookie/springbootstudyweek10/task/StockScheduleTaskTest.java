package com.cookie.springbootstudyweek10.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockScheduleTaskTest {
    @Autowired
    private StockScheduleTask stockScheduleTask;

    @Test
    public void testSendStockMail() {
        // 直接调用定时任务的核心方法，立刻执行
        stockScheduleTask.sendStockHomework();
        System.out.println("测试执行完成，请查看控制台和邮箱");
    }
}