package com.ezreal.ai.code.generator.domain.ai.generate.service.saver;

import com.ezreal.ai.code.generator.domain.ai.generate.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.enums.CodeGenTypeEnum;

/**
 * @author Ezreal
 * @Date 2025/8/23
 */
public class HtmlCodeSaver extends AbstractCodeSaver<HtmlCodeResult>{

    @Override
    protected void validateInput(HtmlCodeResult result) {
        if (result == null) {
            throw new IllegalArgumentException("HtmlCodeResult cannot be null");
        }
    }

    @Override
    protected void saveFile(HtmlCodeResult result, String fileDir) {
        write2File(fileDir, "index.html", result.getHtmlCode());
    }

    @Override
    protected CodeGenTypeEnum getCodeGenType() {
        return CodeGenTypeEnum.HTML;
    }
}
