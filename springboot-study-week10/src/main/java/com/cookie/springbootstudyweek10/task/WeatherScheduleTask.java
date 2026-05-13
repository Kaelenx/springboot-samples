package com.cookie.springbootstudyweek10.task;

import com.cookie.springbootstudyweek10.entity.WeatherData;
import com.cookie.springbootstudyweek10.repository.WeatherDataRepository;
import com.cookie.springbootstudyweek10.service.MailService;
import com.cookie.springbootstudyweek10.service.WeatherService;
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
public class WeatherScheduleTask {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MailService mailService;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Value("${custom.qweather-api-host}")
    private String apiHost;

    @Value("${custom.qweather-api-key}")
    private String apiKey;

    @Value("${custom.qweather-location}")
    private String location;

    @Value("${custom.qweather-email}")
    private String weatherEmail;

    /**
     * 定时任务：每5分钟拉取一次实时天气数据
     * fixedRate = 300000ms = 5分钟
     */
    @Scheduled(fixedRate = 10000) //10s
    public void fetchWeatherData() {
        log.info("========================================");
        log.info("       开始执行天气数据定时拉取       ");
        log.info("========================================");

        try {
            // 1. 获取实时天气数据
            Map<String, String> weatherInfo = weatherService.getRealTimeWeather(apiHost, apiKey, location);

            // 2. 结构化日志输出
            log.info("天气实况 - 气温：{}°C，天气：{}，体感温度：{}°C，湿度：{}%，风向：{}，风速：{}km/h，气压：{}hPa，能见度：{}km",
                    weatherInfo.get("temp"), weatherInfo.get("text"), weatherInfo.get("feelsLike"),
                    weatherInfo.get("humidity"), weatherInfo.get("windDir"), weatherInfo.get("windSpeed"),
                    weatherInfo.get("pressure"), weatherInfo.get("vis"));

            // 3. 保存到数据库
            WeatherData weatherData = new WeatherData();
            weatherData.setTemp(weatherInfo.get("temp"));
            weatherData.setText(weatherInfo.get("text"));
            weatherData.setFeelsLike(weatherInfo.get("feelsLike"));
            weatherData.setHumidity(weatherInfo.get("humidity"));
            weatherData.setWindDir(weatherInfo.get("windDir"));
            weatherData.setWindSpeed(weatherInfo.get("windSpeed"));
            weatherData.setPressure(weatherInfo.get("pressure"));
            weatherData.setVis(weatherInfo.get("vis"));
            weatherData.setObsTime(weatherInfo.get("obsTime"));
            weatherDataRepository.save(weatherData);
            log.info("天气数据已保存到数据库，ID：{}", weatherData.getId());

            // 4. 构建天气报告 HTML 邮件
            String htmlContent = buildWeatherHtmlContent(weatherInfo);
            String subject = String.format("【实时天气报告】%s %s°C - %s",
                    weatherInfo.get("text"),
                    weatherInfo.get("temp"),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 5. 发送邮件
            mailService.sendHtmlMail(weatherEmail, subject, htmlContent);

            log.info("天气数据定时推送成功！");
        } catch (Exception e) {
            log.error("天气数据定时推送失败", e);
        }
    }

    /**
     * 辅助方法：构建天气报告富文本HTML
     */
    private String buildWeatherHtmlContent(Map<String, String> weatherInfo) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>实时天气报告</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; font-family: 'Microsoft YaHei', Arial, sans-serif; background-color: #f5f7fa;\">\n" +
                "    <div style=\"max-width: 600px; margin: 40px auto; padding: 0 20px;\">\n" +
                "        <!-- 头部 -->\n" +
                "        <div style=\"background-color: #3498db; color: #ffffff; padding: 20px; border-radius: 8px 8px 0 0; text-align: center;\">\n" +
                "            <h2 style=\"margin: 0;\">🌤 Spring Boot 定时天气报告</h2>\n" +
                "            <p style=\"margin: 10px 0 0 0; opacity: 0.9;\">实时天气数据</p>\n" +
                "        </div>\n" +
                "        <!-- 内容卡片 -->\n" +
                "        <div style=\"background-color: #ffffff; padding: 30px; border-radius: 0 0 8px 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.1);\">\n" +
                "            <!-- 核心数据 -->\n" +
                "            <div style=\"text-align: center; margin-bottom: 25px;\">\n" +
                "                <p style=\"font-size: 48px; font-weight: bold; margin: 0 0 10px 0; color: #3498db;\">" + weatherInfo.get("temp") + "°C</p>\n" +
                "                <p style=\"font-size: 20px; margin: 0; color: #333333;\">" + weatherInfo.get("text") + "</p>\n" +
                "            </div>\n" +
                "            <hr style=\"border: none; border-top: 1px solid #e4e7ed; margin-bottom: 20px;\">\n" +
                "            <!-- 详细数据表格 -->\n" +
                "            <table style=\"width: 100%; border-collapse: collapse; font-size: 14px;\">\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">天气状况</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("text") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">体感温度</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("feelsLike") + "°C</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">相对湿度</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("humidity") + "%</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">风向</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("windDir") + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">风速</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("windSpeed") + " km/h</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">大气压强</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("pressure") + " hPa</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #606266;\">能见度</td>\n" +
                "                    <td style=\"padding: 10px; border-bottom: 1px solid #e4e7ed; color: #303133; text-align: right;\">" + weatherInfo.get("vis") + " km</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"padding: 10px; color: #606266;\">观测时间</td>\n" +
                "                    <td style=\"padding: 10px; color: #303133; text-align: right;\">" + weatherInfo.get("obsTime") + "</td>\n" +
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
