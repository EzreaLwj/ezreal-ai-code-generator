package com.ezreal.ai.code.generator.domain.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Data
public class MultiFileCodeResult {

    @Description("HTML代码")
    private String htmlCode;

    @Description("CSS代码")
    private String cssCode;

    @Description("JavaScript代码")
    private String jsCode;

    @Description("生成代码的描述")
    private String description;
}
