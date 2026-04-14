package com.cookie.springbootstudyweek04.controller;


import com.cookie.springbootstudyweek04.common.Result;
import com.cookie.springbootstudyweek04.entity.Team;
import com.cookie.springbootstudyweek04.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/team")
@Slf4j
@Validated // 类级别开启校验，支持分组校验、路径参数校验
public class TeamController {

    /**
     * 添加团队接口
     * @param team 前端传入的团队信息，@Valid 触发入参校验
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result<String> addTeam(@Validated @RequestBody Team team, HttpServletRequest request) {
        log.info("收到添加团队请求，参数：{}", team);

        // 1. 从请求头中获取token字段
        String token = request.getHeader("token");

        // 2. 校验token是否为空/空白字符串，为空则抛出401未登录异常
        if (token == null || token.isBlank()) {
            throw new BusinessException(401, "请先登录");
        }

        // 3. 校验token是否有效（示例固定为admin，实际项目替换为JWT/Redis校验逻辑）
        if (!"admin".equals(token)) {
            throw new BusinessException(403, "没有权限");
        }

         int i = 1 / 0;

        return Result.success("添加成功");
    }
}