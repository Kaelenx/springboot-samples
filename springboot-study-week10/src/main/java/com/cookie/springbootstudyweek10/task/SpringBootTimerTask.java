package com.cookie.springbootstudyweek10.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component // 交给 Spring 管理
public class SpringBootTimerTask {

    /**
     * @PostConstruct：该类被 Spring 实例化后，自动执行此方法
     */
    @PostConstruct
    public void initTimer() {
        Timer timer = new Timer("Spring-Boot-Timer-Thread");

        log.info("========================================");
        log.info("       Spring Boot 定时器已启动       ");
        log.info("========================================");

        // 1. 启动定时提醒任务（每5秒一次）
        startReminderTask(timer);

        // 2. 启动倒计时任务（从15秒开始）
        startCountdownTask(timer, 15);
    }

    /**
     * 功能1：定时提醒
     */
    private void startReminderTask(Timer timer) {
        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                log.info("-------------------【定时提醒】-------------------");
                log.info("⏰ 该喝水/休息一下，活动活动颈椎吧！");
                log.info("--------------------------------------------------");
            }
        };
        timer.scheduleAtFixedRate(reminderTask, 0, 5000);
    }

    /**
     * 功能2：倒计时（秒数补零优化）
     */
    private void startCountdownTask(Timer timer, int totalSeconds) {
        AtomicInteger countdown = new AtomicInteger(totalSeconds);

        TimerTask countdownTask = new TimerTask() {
            @Override
            public void run() {
                int current = countdown.get();
                if (current > 0) {
                    // 优化：秒数补零对齐 (09, 08...)
                    String formattedSecond = String.format("%02d", current);
                    log.info("【倒计时】 🕐 {} 秒", formattedSecond);
                    countdown.decrementAndGet();
                } else {
                    log.info("========================================");
                    log.info("       🎉 倒计时结束！时间到！🎉       ");
                    log.info("========================================");
                    this.cancel();
                }
            }
        };
        timer.schedule(countdownTask, 0, 1000);
    }
}