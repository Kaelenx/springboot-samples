package com.cookie.springbootstudyweek10.task;

import com.cookie.springbootstudyweek10.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class BirthdayScheduleTask {

    @Autowired
    private MailService mailService;

    @Value("${custom.birthday-email}")
    private String birthdayEmail;

    @Value("${custom.birthday-name}")
    private String birthdayName;

    /**
     * 定时任务：每年5月12日16:30发送生日祝福邮件
     * Cron表达式：秒 分 时 日 月 周
     * 0 30 16 12 5 ? = 每年5月12日16:30
     */
    @Scheduled(cron = "0 09 20 13 5 ?")
    public void sendBirthdayGreeting() {
        log.info("========================================");
        log.info("       开始执行生日祝福邮件推送       ");
        log.info("========================================");

        try {
            // 1. 构建生日祝福 HTML 邮件内容
            String htmlContent = buildBirthdayHtmlContent();

            // 2. 构建邮件主题
            String subject = String.format("生日快乐，%s！🎂 - %s",
                    birthdayName,
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            // 3. 发送邮件
            mailService.sendHtmlMail(birthdayEmail, subject, htmlContent);

            log.info("生日祝福邮件推送成功！");
        } catch (Exception e) {
            log.error("生日祝福邮件推送失败", e);
        }
    }

    /**
     * 辅助方法：构建生日祝福富文本HTML
     */
    private String buildBirthdayHtmlContent() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>生日快乐</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; font-family: 'Microsoft YaHei', Arial, sans-serif; background-color: #f5f7fa;\">\n" +
                "    <div style=\"max-width: 600px; margin: 40px auto; padding: 0 20px;\">\n" +
                "        <!-- 头部 -->\n" +
                "        <div style=\"background: linear-gradient(135deg, #ff6b6b, #feca57); color: #ffffff; padding: 30px; border-radius: 8px 8px 0 0; text-align: center;\">\n" +
                "            <h1 style=\"margin: 0; font-size: 32px;\">🎂 生日快乐！🎂</h1>\n" +
                "            <p style=\"margin: 15px 0 0 0; font-size: 18px; opacity: 0.9;\">Happy Birthday to " + birthdayName + "</p>\n" +
                "        </div>\n" +
                "        <!-- 内容卡片 -->\n" +
                "        <div style=\"background-color: #ffffff; padding: 30px; border-radius: 0 0 8px 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.1);\">\n" +
                "            <div style=\"text-align: center; margin-bottom: 25px;\">\n" +
                "                <p style=\"font-size: 48px; margin: 0;\">🎈🎁🎉</p>\n" +
                "            </div>\n" +
                "            <p style=\"color: #333333; font-size: 16px; line-height: 1.8; text-align: center; margin: 0 0 20px 0;\">\n" +
                "                亲爱的 <strong style=\"color: #ff6b6b;\">" + birthdayName + "</strong>，\n" +
                "            </p>\n" +
                "            <p style=\"color: #333333; font-size: 16px; line-height: 1.8; text-align: center; margin: 0 0 20px 0;\">\n" +
                "                在这个特别的日子里，祝你生日快乐！🎊\n" +
                "            </p>\n" +
                "            <p style=\"color: #333333; font-size: 16px; line-height: 1.8; text-align: center; margin: 0 0 20px 0;\">\n" +
                "                愿你在新的一岁里，健康平安，万事胜意，\n" +
                "            </p>\n" +
                "            <p style=\"color: #333333; font-size: 16px; line-height: 1.8; text-align: center; margin: 0 0 30px 0;\">\n" +
                "                所有美好的事情都会如期而至！🌟\n" +
                "            </p>\n" +
                "            <hr style=\"border: none; border-top: 1px solid #e4e7ed; margin-bottom: 25px;\">\n" +
                "            <p style=\"color: #909399; font-size: 14px; text-align: center; margin: 0;\">\n" +
                "                " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")) + "\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <!-- 页脚 -->\n" +
                "        <div style=\"text-align: center; margin-top: 20px; color: #909399; font-size: 12px;\">\n" +
                "            <p>此邮件由 Spring Boot 定时任务自动发送</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
