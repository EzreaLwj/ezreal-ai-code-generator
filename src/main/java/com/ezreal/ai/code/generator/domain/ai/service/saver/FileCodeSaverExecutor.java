package com.ezreal.ai.code.generator.domain.ai.service.saver;

import com.ezreal.ai.code.generator.domain.ai.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.MultiFileCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;

import java.io.File;

/**
 * @author Ezreal
 * @Date 2025/8/23
 */
public class FileCodeSaverExecutor {

    private final static HtmlCodeSaver htmlCodeSaver = new HtmlCodeSaver();

    private final static MultiFileCodeSaver multiFileCodeSaver = new MultiFileCodeSaver();

    public static File execute(Object result, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> htmlCodeSaver.saveCode((HtmlCodeResult) result);
            case MULTI_FILE -> multiFileCodeSaver.saveCode((MultiFileCodeResult) result);
            default -> throw new IllegalArgumentException("不支持的代码生成类型");
        };
    }
}
