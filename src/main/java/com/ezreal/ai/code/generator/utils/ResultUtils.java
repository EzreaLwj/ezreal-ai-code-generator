package com.ezreal.ai.code.generator.utils;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.enums.ResponseCode;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应
     */
    public static <T> Response<T> success(T data) {
        return new Response<>("0000", "ok", data);
    }
    /**
     * 失败
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 响应
     */
    public static Response<?> error(String code, String message) {
        return new Response<>(code, message, null);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static Response<?> error(ResponseCode errorCode, String message) {
        return new Response<>(errorCode.getCode(), message, null);
    }
}
