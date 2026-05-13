package com.cookie.springbootstudyweek08.stats.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PostViewIncrementRequest(
        @NotNull(message = "增量不能为空")
        @Min(value = 1, message = "增量不能小于 1")
        @Max(value = 1000, message = "增量不能大于 1000")
        Integer delta
) {
}
