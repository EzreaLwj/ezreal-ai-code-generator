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
    NO_AUTH("0005", "无权限"),
    BLOG_NO_FOUND("0006", "blog不存在"),
    NO_DATA("0007", "无数据"),
    LIKE_EXIST("0008", "已点赞"),
    LIKE_NOT_EXIST("0009", "未点赞"),
    USER_NOT_LOGIN("0004", "用户未登录"),
    ;

    ResponseCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    private String code;
    private String info;
}
