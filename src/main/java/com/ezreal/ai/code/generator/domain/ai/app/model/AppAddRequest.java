package com.ezreal.ai.code.generator.domain.ai.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppAddRequest {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 应用初始化的 prompt
     */
    private String initPrompt;

    /**
     * 代码生成类型（枚举）
     */
    private String codeGenType;
}
