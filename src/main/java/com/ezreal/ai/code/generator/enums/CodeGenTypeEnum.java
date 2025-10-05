package com.ezreal.ai.code.generator.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Getter
@AllArgsConstructor
public enum CodeGenTypeEnum {

    HTML("html", "原生HTML模式"),
    MULTI_FILE("multi_file", "原生多文件模式"),
    ;

    private final String value;
    private final String text;


    public static CodeGenTypeEnum getByValue(String value) {
        if (ObjUtil.isNull(value)) {
            return null;
        }
        for (CodeGenTypeEnum item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }
}
