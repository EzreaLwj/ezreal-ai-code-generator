package com.ezreal.ai.code.generator.domain.ai.service.parser;

import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;
import com.ezreal.ai.code.generator.exception.AppException;

/**
 * 代码解析执行器
 *
 * @author Ezreal
 * @Date 2025/8/23
 */
public class CodeParserExecutor {

    private final static HtmlCodeParser htmlCodeParser = new HtmlCodeParser();

    private final static MultiFileCodeParser multiFileCodeParser = new MultiFileCodeParser();

    /**
     * 执行代码解析
     *
     * @param code          代码
     * @param codeGenTypeEnum 代码生成类型
     * @return 解析结果
     */
    public static Object execute(String code, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> htmlCodeParser.parse(code);
            case MULTI_FILE -> multiFileCodeParser.parse(code);
            default -> throw new AppException("不支持的代码生成类型");
        };
    }
}
