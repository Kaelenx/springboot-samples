package com.cookie.springbootstudyweek04.controller;

import com.cookie.springbootstudyweek04.common.Result;
import com.cookie.springbootstudyweek04.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;

/**
 * @author mqxu
 * @date 2026/3/26
 * @description UserController 测试接口
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 获取用户信息，测试消息转换器是否生效
     */
    @GetMapping("/info")
    public Result<User> getUserInfo() {
        User user = new User();
        // 超长Long值，超过JS最大安全整数，专门测试精度丢失问题
        user.setId(1234567890123456789L);
        user.setUsername("springmvc-student");
        user.setCreateTime(LocalDateTime.now());
        return Result.success(user);
    }
}
