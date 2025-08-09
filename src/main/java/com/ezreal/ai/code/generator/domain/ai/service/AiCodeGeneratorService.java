package com.ezreal.ai.code.generator.domain.ai.service;

import com.ezreal.ai.code.generator.domain.ai.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.SystemMessage;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
public interface AiCodeGeneratorService {

    String generateCode(String userMessage);

    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    HtmlCodeResult generateHtmlCode(String userMessage);

    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    MultiFileCodeResult generateMultiFileCode(String userMessage);
}
