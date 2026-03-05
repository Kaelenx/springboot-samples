package com.cookie.springhello.vo;

import lombok.Data;

/**
 * 全局统一返回结果封装类
 */
@Data
public class ResultVO<T> {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 空参构造（用于反序列化/框架自动装配）
     */
    public ResultVO() {
    }

    /**
     * 全参构造
     */
    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // -------------------------- 成功响应快捷方法 --------------------------
    /**
     * 快速构建成功响应（带数据）
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(200, "success", data);
    }

    /**
     * 快速构建成功响应（无数据，仅提示）
     */
    public static <T> ResultVO<T> success() {
        return new ResultVO<>(200, "success", null);
    }

    /**
     * 快速构建成功响应（自定义消息+数据）
     */
    public static <T> ResultVO<T> success(String msg, T data) {
        return new ResultVO<>(200, msg, data);
    }

    // -------------------------- 错误响应快捷方法 --------------------------
    /**
     * 快速构建错误响应（自定义状态码+消息）
     */
    public static <T> ResultVO<T> error(Integer code, String msg) {
        return new ResultVO<>(code, msg, null);
    }

    /**
     * 快速构建通用服务器错误响应（500）
     */
    public static <T> ResultVO<T> serverError() {
        return new ResultVO<>(500, "服务器内部错误", null);
    }

    /**
     * 快速构建通用参数错误响应（400）
     */
    public static <T> ResultVO<T> badRequest(String msg) {
        return new ResultVO<>(400, msg, null);
    }

    /**
     * 快速构建通用未授权响应（401）
     */
    public static <T> ResultVO<T> unauthorized(String msg) {
        return new ResultVO<>(401, msg, null);
    }

    /**
     * 快速构建通用禁止访问响应（403）
     */
    public static <T> ResultVO<T> forbidden(String msg) {
        return new ResultVO<>(403, msg, null);
    }

    /**
     * 快速构建通用资源不存在响应（404）
     */
    public static <T> ResultVO<T> notFound(String msg) {
        return new ResultVO<>(404, msg, null);
    }
}