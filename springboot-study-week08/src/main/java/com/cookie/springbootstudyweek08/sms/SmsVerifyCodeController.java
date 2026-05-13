package com.cookie.springbootstudyweek08.sms;

import com.cookie.springbootstudyweek08.sms.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsVerifyCodeController {

    private final SmsVerifyCodeService smsVerifyCodeService;

    @PostMapping("/verify-codes")
    public ApiResult<SendCodeResponse> send(@RequestBody @Valid SendCodeRequest request) {
        return ApiResult.success(smsVerifyCodeService.sendCode(request.phone()));
    }

    @PostMapping("/verify-codes/validate")
    public ResponseEntity<ApiResult<ValidateCodeView>> validate(@RequestBody @Valid ValidateCodeRequest request) {
        if (!smsVerifyCodeService.validateCode(request.phone(), request.code())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(ApiResult.error(422, "验证码错误或已过期"));
        }
        return ResponseEntity.ok(ApiResult.success(new ValidateCodeView(true)));
    }
}
