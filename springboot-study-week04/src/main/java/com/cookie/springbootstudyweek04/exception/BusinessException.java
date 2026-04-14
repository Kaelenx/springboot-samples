package com.cookie.springbootstudyweek04.exception;

import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {
    // 自定义错误码
    private final int code;

    /**
     * 重载构造方法1：仅传入错误消息，错误码默认500
     * @param msg 业务错误提示
     */
    public BusinessException(String msg) {
        super(msg);
        this.code = 500;
    }

    /**
     * 重载构造方法2：自定义错误码+错误消息
     * @param code 自定义错误码（如401/403）
     * @param msg 业务错误提示
     */
    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}