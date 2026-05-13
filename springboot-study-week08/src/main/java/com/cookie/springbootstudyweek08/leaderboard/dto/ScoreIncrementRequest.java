package com.cookie.springbootstudyweek08.leaderboard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ScoreIncrementRequest(
        @NotNull(message = "用户 ID 不能为空")
        @Positive(message = "用户 ID 必须大于 0")
        Long userId,
        @NotNull(message = "积分增量不能为空")
        @Min(value = 1, message = "积分增量不能小于 1")
        @Max(value = 1000, message = "积分增量不能大于 1000")
        Integer scoreDelta
) {
}
