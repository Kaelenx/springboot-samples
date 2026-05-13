package com.cookie.springbootstudyweek10.task;

import com.cookie.springbootstudyweek10.service.MailService;
import com.cookie.springbootstudyweek10.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Component
public class StockScheduleTask {

    @Autowired
    private StockService stockService;

    @Autowired
    private MailService mailService;

    @Value("${custom.teacher-email}")
    private String teacherEmail;

    @Value("${custom.stock-code}")
    private String stockCode;

    /**
     * 定时任务：每日17:10发送股票作业
     * Cron表达式：秒 分 时 日 月 周
     * 通用版：0 10 17 * * ?（每天17:10）
     * 今日版（如2026-05-07）：0 10 17 7 5 ? 2026
     */
    @Scheduled(cron = "0 46 16 * * ?")
    public void sendStockHomework() {
        log.info("========================================");
        log.info("       开始执行股票作业定时推送       ");
        log.info("========================================");

        try {
            // 1. 获取实时股票数据
            Map<String, String> stockInfo = stockService.getRealTimeStockInfo(stockCode);

            // 2. 构建富文本邮件内容（红涨绿跌，排版清晰）
            String htmlContent = buildStockHtmlContent(stockInfo);

            // 3. 发送邮件
            String subject = String.format("【Spring Boot 定时作业】%s 实时股票行情 - %s",
                    stockInfo.get("name"),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            mailService.sendHtmlMail(teacherEmail, subject, htmlContent);

            log.info("✅ 股票作业推送成功！");
        } catch (Exception e) {
            log.error("❌ 股票作业推送失败", e);
        }
    }

    /**
     * 辅助方法：构建红涨绿跌的富文本HTML
     */
    private String buildStockHtmlContent(Map<String, String> stockInfo) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>股票作业</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; font-family: 'Microsoft YaHei', Arial, sans-serif; background-color: #f5f7fa;\">\n" +
                "    <div style=\"max-width: 600px; margin: 40px auto; padding: 0 20px;\">\n" +
                "        <!-- 头部 -->\n" +
                "        <div style=\"background-color: #409eff; color: #ffffff; padding: 20px; border-radius: 8px 8px 0 0; text-align: center;\">\n" +
                "            <h2 style=\"margin: 0;\">📊 Spring Boot 定时作业推送</h2>\n" +
                "            <p style=\"margin: 10px 0 0 0; opacity: 0.9;\">实时股票行情</p>\n" +
                "        </div>\n" +
                "        <!-- 内容卡片 -->\n" +
                "        <div style=\"background-color: #ffffff; padding: 30px; border-radius: 0 0 8px 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.1);\">\n" +
                "            <h3 style=\"color: #333333; margin: 0 0 20px 0; text-align: center;\">" + stockInfo.get("name") + " (" + stockInfo.get("code") + ")</h3>\n" +
                "            <hr style=\"border: none; border-top: 1px solid #e4e7ed; margin-bottom: 20px;\">\n" +
                "            <!-- 核心数据 -->\n" +
                "            <div style=\"text-align: center; margin-bottom: 25px;\">\n" +
                "                <p style=\"font-size: 36px; font-weight: bold; margin: 0 0 10px 0; color: " + stockInfo.get("changeColor") + ";\">" + stockInfo.get("current") + "</p>\n" +
                "                <p style=\"font-size: 18px; margin: 0; color: " + stockInfo.get("changeColor") + ";\">" + stockInfo.get("changePercent") + "</p>\n" +
                "            </div>\n" +
                "            <!-- 详细数据表格 -->\n" +
                "            <table style=\"width: 100%; border-collapse: collapse; font-size: 14px;\">\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">今日开盘</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + stockInfo.get("open") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">昨日收盘</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + stockInfo.get("close") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">今日最高</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #ff4d4f; text-align: right;\">" + stockInfo.get("high") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">今日最低</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #52c41a; text-align: right;\">" + stockInfo.get("low") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">成交量（手）</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + stockInfo.get("volume") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; color: #606266;\">成交额（元）</td>\n" +
                "                    <td style=\"padding: 10px; color: #303133; text-align: right;\">" + stockInfo.get("amount") + "</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <!-- 页脚 -->\n" +
                "        <div style=\"text-align: center; margin-top: 20px; color: #909399; font-size: 12px;\">\n" +
                "            <p>此邮件由 Spring Boot 定时任务自动发送，请勿直接回复</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
