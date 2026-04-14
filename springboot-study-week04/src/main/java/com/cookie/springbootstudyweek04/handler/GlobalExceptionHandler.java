package com.cookie.springbootstudyweek04.handler;

import com.cookie.springbootstudyweek04.common.Result;
import com.cookie.springbootstudyweek04.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;


@Slf4j
@RestControllerAdvice // 对所有@RestController接口进行全局增强，自动捕获异常
public class GlobalExceptionHandler {

    /**
     * 处理【参数校验异常】：@Validated/@Valid 校验失败抛出的异常
     * @param e 参数校验异常
     * @return 统一Result格式，错误码400，拼接所有校验错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 设置HTTP响应状态码为400
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        // 拼接所有字段的校验错误信息，用分号分隔
        StringJoiner errorMsg = new StringJoiner("；");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMsg.add(fieldError.getDefaultMessage());
        }
        log.error("参数校验失败：{}", errorMsg);
        return Result.error(400, errorMsg.toString());
    }

    /**
     * 处理【自定义业务异常】：手动抛出的BusinessException
     * @param e 业务异常
     * @return 统一Result格式，使用异常中自定义的错误码和消息
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        // 业务异常仅打印警告日志，不打印堆栈，避免日志冗余
        log.warn("业务异常触发：错误码={}, 错误信息={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 【兜底异常处理】：捕获所有其他未处理的系统异常
     * @param e 通用异常
     * @return 统一Result格式，错误码500，返回友好提示
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 设置HTTP响应状态码为500
    public Result<?> handleException(Exception e) {
        // 系统异常打印完整堆栈，方便排查问题
        log.error("系统未捕获异常", e);
        return Result.error(500, "服务器异常，请稍后重试");
    }
}