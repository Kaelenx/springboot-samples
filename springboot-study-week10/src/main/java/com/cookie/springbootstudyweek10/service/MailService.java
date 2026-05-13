package com.cookie.springbootstudyweek10.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // 从配置文件读取发件人邮箱，避免硬编码不一致
    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("244859845@qq.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 新增：HTML富文本邮件发送
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param htmlContent HTML富文本内容
     */
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 第二个参数true：开启multipart，支持HTML/附件
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setSubject(subject);
            // 第二个参数true：标识内容为HTML格式
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("富文本邮件发送失败", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 正文（支持HTML）
     * @param attachments 附件文件列表
     */
    public void sendMailWithAttachment(String to, String subject, String content, List<File> attachments) {
        try {
            // 1. 创建 MimeMessage 对象
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // 2. 设置收件人、主题和内容 (true = 开启 multipart 模式，支持附件)
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true 表示内容为 HTML

            // 3. 将附件文件对象加入 MimeMessage
            if (attachments != null && !attachments.isEmpty()) {
                for (File file : attachments) {
                    // addAttachment(邮件中显示的文件名, 文件对象)
                    helper.addAttachment(file.getName(), file);
                }
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("带附件邮件发送失败", e);
        }
    }
}