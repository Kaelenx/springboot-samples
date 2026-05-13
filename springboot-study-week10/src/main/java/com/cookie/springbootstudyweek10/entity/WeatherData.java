package com.cookie.springbootstudyweek10.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 温度 */
    private String temp;

    /** 天气状况文字描述 */
    private String text;

    /** 体感温度 */
    private String feelsLike;

    /** 相对湿度 */
    private String humidity;

    /** 风向 */
    private String windDir;

    /** 风速 */
    private String windSpeed;

    /** 大气压强 */
    private String pressure;

    /** 能见度 */
    private String vis;

    /** 观测时间 */
    private String obsTime;

    /** 数据入库时间 */
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }
}
