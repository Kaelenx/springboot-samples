package com.cookie.springbootstudyweek10.task;

import com.cookie.springbootstudyweek10.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherScheduleTaskTest {

    @Autowired
    private WeatherScheduleTask weatherScheduleTask;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testFetchWeatherData() {
        weatherScheduleTask.fetchWeatherData();
        System.out.println("天气数据定时任务测试完成，请查看控制台和邮箱");
    }

    @Test
    public void testGetRealTimeWeather() {
        Map<String, String> weatherInfo = weatherService.getRealTimeWeather(
                "kg78m32vp4.re.qweatherapi.com", "5b4560966fa44a2bb2bfafac59862d6d", "101190101");
        assertNotNull(weatherInfo);
        assertNotNull(weatherInfo.get("temp"));
        System.out.println("天气数据获取成功：" + weatherInfo);
    }
}
