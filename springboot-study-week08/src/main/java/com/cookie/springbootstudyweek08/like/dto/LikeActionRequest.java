package com.cookie.springbootstudyweek08.like.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeActionRequest(
        @NotNull(message = "用户 ID 不能为空")
        @Positive(message = "用户 ID 必须大于 0")
        Long userId
) {
}
