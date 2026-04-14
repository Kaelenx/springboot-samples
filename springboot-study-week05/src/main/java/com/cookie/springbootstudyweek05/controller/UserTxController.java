package com.cookie.springbootstudyweek05.controller;



import com.cookie.springbootstudyweek05.common.Result;
import com.cookie.springbootstudyweek05.entity.User;
import com.cookie.springbootstudyweek05.service.UserTxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

/**
 * 事务测试接口控制器
 * 严格匹配PDF规范：仅做接口暴露，不写业务逻辑
 */
@RestController
@RequestMapping("/api/user/tx")
@RequiredArgsConstructor
public class UserTxController {

    private final UserTxService userTxService;

    /**
     * 事务测试接口：新增两个用户，验证事务回滚
     * @param map 包含user1和user2的用户数据
     * @return 操作结果
     */
    @PostMapping("/addTwo")
    public Result<String> addTwo(@RequestBody Map<String, User> map) {
        User user1 = map.get("user1");
        User user2 = map.get("user2");
        // 调用带事务的业务方法
        userTxService.addTwoUsers(user1, user2);
        return Result.success("两个用户均新增成功");
    }
}