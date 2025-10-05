package com.ezreal.ai.code.generator.domain.ai.generate.service.saver;

import com.ezreal.ai.code.generator.domain.ai.generate.model.MultiFileCodeResult;
import com.ezreal.ai.code.generator.enums.CodeGenTypeEnum;

/**
 * @author Ezreal
 * @Date 2025/8/23
 */
public class MultiFileCodeSaver extends AbstractCodeSaver<MultiFileCodeResult>{

    @Override
    protected void validateInput(MultiFileCodeResult result) {
        if (result == null) {
            throw new IllegalArgumentException("result cannot be null");
        }
    }

    @Override
    protected void saveFile(MultiFileCodeResult result, String fileDir) {
        write2File(fileDir, "index.html", result.getHtmlCode());
        write2File(fileDir, "index.css", result.getCssCode());
        write2File(fileDir, "index.js", result.getJsCode());
    }

    @Override
    protected CodeGenTypeEnum getCodeGenType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

}
