package com.ezreal.ai.code.generator.domain.ai.app.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Data
@Builder
public class AppUpdateRequest {

    private String appName;
    private Long appId;
}
