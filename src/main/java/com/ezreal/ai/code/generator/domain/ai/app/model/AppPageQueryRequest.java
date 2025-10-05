package com.ezreal.ai.code.generator.domain.ai.app.model;


import com.ezreal.ai.code.generator.common.PageRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class AppPageQueryRequest extends PageRequest {

    private String appName;
}
