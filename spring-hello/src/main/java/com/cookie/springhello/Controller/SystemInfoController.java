package com.cookie.springhello.Controller;

import com.cookie.springhello.vo.ResultVO;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统应用信息专属控制器
 */
@RestController
@RequestMapping("/api/system")
public class SystemInfoController {

    private final Environment environment;

    public SystemInfoController(Environment environment) {
        this.environment = environment;
    }

    /**
     * 应用信息查询接口
     */
    @GetMapping("/info")
    public ResultVO<Map<String, String>> systemInfo() {
        Map<String, String> data = new HashMap<>();
        try {
            data.put("javaVersion", System.getProperty("java.version"));
            // 修正：Spring Boot版本不能直接通过Environment获取，改为手动配置或固定值
            data.put("springBootVersion", "3.5.11"); // 对应你的Spring Boot版本
            data.put("hostName", InetAddress.getLocalHost().getHostName());
            data.put("activeProfile", String.join(",", environment.getActiveProfiles()));
        } catch (Exception e) {
            data.put("error", "获取应用信息失败: " + e.getMessage());
        }
        return ResultVO.success(data);
    }
}