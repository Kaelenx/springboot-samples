package com.cookie.springbootstudyweek08.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidateCodeRequest(
        @NotBlank(message = "手机号不能为空")
        @Pattern(regexp = "^1\\d{10}$", message = "请输入 11 位国内手机号")
        String phone,
        @NotBlank(message = "验证码不能为空")
        @Pattern(regexp = "^\\d{6}$", message = "请输入 6 位数字验证码")
        String code
) {
}
