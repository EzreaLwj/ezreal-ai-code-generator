package com.ezreal.ai.code.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    ADMIN("admin", "管理员"),

    USER("user", "普通用户");

    private final String value;
    private final String info;

}
