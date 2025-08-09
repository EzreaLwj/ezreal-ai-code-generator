package com.ezreal.ai.code.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Getter
public enum ResponseCode {

    SUCCESS("0000", "调用成功"),
    UN_ERROR("0001", "调用失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    SYSTEM_ERROR("0003", "系统异常"),
    ;

    ResponseCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    private String code;
    private String info;
}
