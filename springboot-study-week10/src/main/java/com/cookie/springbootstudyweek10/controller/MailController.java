package com.cookie.springbootstudyweek10.controller;

import com.cookie.springbootstudyweek10.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送简单邮件接口
     * 请求示例: POST /api/mail/send?to=xxx@qq.com&subject=测试&content=你好
     */
    @PostMapping("/send")
    public String sendSimpleMail(@RequestParam String to,
                                 @RequestParam String subject,
                                 @RequestParam String content) {
        try {
            mailService.sendSimpleMail(to, subject, content);
            return "✅ 邮件发送成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ 邮件发送失败: " + e.getMessage();
        }
    }

    // 新增：注册激活富文本邮件发送接口
    @PostMapping("/send-activate-html")
    public String sendActivateHtmlMail(@RequestParam String to) {
        try {
            // 邮件主题
            String subject = "恭喜您注册成功！请激活您的账号";
            // 富文本HTML内容（上面的完整HTML，这里压缩成单行字符串，也可以单独抽成模板）
            String htmlContent = "<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>账号激活</title></head><body style=\"margin: 0; padding: 0; font-family: 'Microsoft YaHei', Arial, sans-serif; background-color: #ffffff;\"><div style=\"max-width: 600px; margin: 50px auto; padding: 0 20px;\"><div style=\"background-color: #f9f9f9; border-radius: 4px; padding: 40px 30px;\"><h2 style=\"color: #409eff; text-align: center; margin: 0 0 20px 0;\">欢迎加入我们！</h2><hr style=\"border: none; border-top: 2px solid #409eff; margin-bottom: 30px;\"><p style=\"color: #333333; font-size: 16px; line-height: 1.8; margin: 0 0 20px 0;\">尊敬的用户，您好！</p><p style=\"color: #333333; font-size: 16px; line-height: 1.8; margin: 0 0 30px 0;\">恭喜您成功注册我们的平台。为了保障您的账号安全，请点击下方按钮激活您的账号：</p><div style=\"text-align: center; margin: 0 0 30px 0;\"><a href=\"https://example.com/activate?token=YOUR_ACTIVATION_TOKEN\" style=\"display: inline-block; background-color: #409eff; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 4px; font-size: 16px; font-weight: 500;\">立即激活账号</a></div><p style=\"color: #666666; font-size: 14px; line-height: 1.6; margin: 0 0 10px 0;\">如果按钮无法点击，您可以复制以下链接到浏览器中打开：</p><div style=\"background-color: #ffffff; border: 1px solid #eaecef; border-radius: 4px; padding: 10px; margin: 0 0 20px 0;\"><a href=\"https://example.com/activate?token=YOUR_ACTIVATION_TOKEN\" style=\"color: #409eff; font-size: 14px; word-break: break-all;\">https://example.com/activate?token=YOUR_ACTIVATION_TOKEN</a></div><p style=\"color: #999999; font-size: 14px; line-height: 1.6; margin: 0;\">温馨提示：激活链接有效期为24小时，请尽快完成激活。</p></div><div style=\"text-align: center; margin-top: 40px; color: #999999; font-size: 12px; line-height: 1.8;\"><p style=\"margin: 0 0 5px 0;\">此邮件由系统自动发送，请勿直接回复</p><p style=\"margin: 0;\">© 2026 Your Company. All rights reserved.</p></div></div></body></html>";

            // 调用发送方法
            mailService.sendHtmlMail(to, subject, htmlContent);
            return "✅ 注册激活富文本邮件发送成功！请前往邮箱查看";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ 富文本邮件发送失败: " + e.getMessage();
        }
    }
}