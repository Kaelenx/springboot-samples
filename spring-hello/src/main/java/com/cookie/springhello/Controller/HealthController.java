package com.cookie.springhello.Controller;

import com.cookie.springhello.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康检查专属控制器
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @Value("${spring.application.name:unknown}")
    private String projectName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public ResultVO<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", projectName);
        data.put("version", appVersion);
        data.put("serverTime", LocalDateTime.now().toString());
        data.put("status", "UP");
        return ResultVO.success(data);
    }
}
