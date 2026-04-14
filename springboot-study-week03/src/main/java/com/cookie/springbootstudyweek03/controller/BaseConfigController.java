package com.cookie.springbootstudyweek03.controller;

import com.cookie.springbootstudyweek03.config.AppConfig;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class BaseConfigController {

    @Value("${server.port}")
    private Integer port;

    @Value("${app.name}")
    private String appName;

    @Value("${app.description}")
    private String appDescription;

    @Value("${app.version}")
    private String appVersion;

    @Value("${spring.application.name}")
    private String springApplicationName;

    @GetMapping("/Port")
    public String getPort() {
        return "当前端口是：" + port;
    }

    @GetMapping("/AppName")
    public String getAppName() {
        return "当前应用名称是：" + appName;
    }

    @GetMapping("/AppDescription")
    public String getAppDescription() {
        return "当前应用描述是：" + appDescription;
    }

    @GetMapping("/AppVersion")
    public String getAppVersion() {
        return "当前应用版本是：" + appVersion;
    }

    @GetMapping("/info")
    public String getAllInfo() {
        return "当前端口是：" + port + "，\n当前应用名称是：" + appName + "，\n当前应用描述是：" + appDescription + "，\n当前应用版本是：" + appVersion + "，\n当前Spring应用名称是：" + springApplicationName;
    }

    @Resource
    private AppConfig appConfig;

    @GetMapping("/allInfo")
    public Map<String, Object> getAllInfoByAppConfig() {
        return Map.of(
                "name", appConfig.getName(),
                "version", appConfig.getVersion(),
                "description", appConfig.getDescription()
        );

    }

}
