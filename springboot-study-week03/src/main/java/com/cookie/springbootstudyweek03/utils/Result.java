package com.cookie.springbootstudyweek03.utils;

import lombok.Data;
import java.io.Serializable;

/**
 * 通用返回结果类（全项目复用，无业务耦合）
 * 适配所有接口场景：成功/失败、带数据/不带数据
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通用状态码：200成功，500失败（可自定义扩展）
     */
    private int code;

    /**
     * 返回消息（成功/失败描述）
     */
    private String msg;

    /**
     * 返回数据（泛型，支持任意类型：String/Map/自定义对象）
     */
    private T data;

    // ========== 静态工厂方法（快速创建结果，避免new和空指针） ==========
    /**
     * 成功（无数据）
     */
    public static <T> Result<T> success() {
        return success(null, "操作成功");
    }

    /**
     * 成功（带数据）
     */
    public static <T> Result<T> success(T data) {
        return success(data, "操作成功");
    }

    /**
     * 成功（自定义消息+数据）
     */
    public static <T> Result<T> success(T data, String msg) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 失败（自定义消息）
     */
    public static <T> Result<T> fail(String msg) {
        return fail(500, msg, null);
    }

    /**
     * 失败（自定义状态码+消息）
     */
    public static <T> Result<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 失败（全自定义）
     */
    public static <T> Result<T> fail(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}