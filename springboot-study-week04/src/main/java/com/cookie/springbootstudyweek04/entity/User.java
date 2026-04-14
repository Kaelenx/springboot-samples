package com.cookie.springbootstudyweek04.entity;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class User {
    // 用户ID（Long类型，解决前端精度丢失）
    private Long id;
    // 用户名
    private String username;
    // 创建时间（统一日期格式）
    private LocalDateTime createTime;
}
