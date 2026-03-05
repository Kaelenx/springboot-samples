package com.cookie.springhello.Controller;

import com.cookie.springhello.vo.ResultVO;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统环境变量专属控制器
 */
@RestController
@RequestMapping("/api/system")
public class SystemEnvController {

    // 注入Environment对象（解决getProperty找不到符号的核心）
    private final Environment environment;

    // 构造函数注入Environment
    public SystemEnvController(Environment environment) {
        this.environment = environment;
    }

    /**
     * 环境变量查询接口（仅返回安全信息）
     */
    @GetMapping("/env")
    public ResultVO<Map<String, String>> systemEnv() {
        Map<String, String> data = new HashMap<>();
        Map<String, String> env = System.getenv();

        // 只筛选非敏感的环境变量
        if (env.containsKey("JAVA_HOME")) {
            data.put("JAVA_HOME", env.get("JAVA_HOME"));
        }
        if (env.containsKey("USER")) {
            data.put("USER", env.get("USER"));
        }
        if (env.containsKey("OS")) {
            data.put("OS", env.get("OS"));
        }

        return ResultVO.success(data);
    }
}
