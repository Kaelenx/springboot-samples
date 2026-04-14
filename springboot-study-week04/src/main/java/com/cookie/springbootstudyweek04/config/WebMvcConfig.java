package com.cookie.springbootstudyweek04.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    /**
     * 自定义 Jackson 消息转换器
     * 解决2个核心问题：LocalDateTime日期格式统一、Long类型转字符串避免前端精度丢失
     */
    @Bean
    public MappingJackson2HttpMessageConverter customJacksonConverter() {
        // 定义全局日期格式化规则
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 【红框核心代码】构建 ObjectMapper，配置序列化规则
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(
                        // 模块1：Java8时间类型处理模块，自定义LocalDateTime的序列化格式
                        new JavaTimeModule()
                                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter)),
                        // 模块2：自定义简单模块，处理Long类型转字符串
                        new SimpleModule()
                                .addSerializer(Long.class, ToStringSerializer.instance)
                                .addSerializer(Long.TYPE, ToStringSerializer.instance) // 兼容基本类型long
                )
                .build();

        // 用自定义的ObjectMapper，创建消息转换器并交给Spring管理
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射到实际的上传目录
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/");
    }
}
