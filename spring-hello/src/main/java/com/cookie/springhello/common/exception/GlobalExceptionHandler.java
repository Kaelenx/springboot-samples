package com.cookie.springhello.common.exception;

import com.cookie.springhello.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有未捕获的异常（通用服务器错误）
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<Void> handleException(Exception e) {
        // 生产环境建议隐藏具体异常信息，只返回通用提示
        return ResultVO.error(500, "系统内部错误: " + e.getMessage());
        // 生产环境替换为：return ResultVO.serverError();
    }

    /**
     * 处理参数非法异常（400）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVO<Void> handleIllegalArgException(IllegalArgumentException e) {
        return ResultVO.badRequest("参数错误: " + e.getMessage());
    }

    /**
     * 处理空指针异常（自定义提示）
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVO<Void> handleNullPointerException(NullPointerException e) {
        return ResultVO.error(500, "空指针异常: " + e.getMessage());
    }
}
