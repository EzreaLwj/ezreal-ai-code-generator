package com.ezreal.ai.code.generator;

import cn.hutool.json.JSONUtil;
import com.ezreal.ai.code.generator.domain.ai.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.MultiFileCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;
import com.ezreal.ai.code.generator.domain.ai.service.AiCodeGeneratorFacade;
import com.ezreal.ai.code.generator.domain.ai.service.AiCodeGeneratorService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Slf4j
@SpringBootTest
public class AiGenerateCodeTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;
    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    public void testGenerateCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做一个记录的小工具");
        log.info("输出结果为：{}", JSONUtil.toJsonStr(result));
    }

    @Test
    public void testGenerateMultiFileCode() {
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode("做一个留言板工具");
        log.info("输出结果为：{}", JSONUtil.toJsonStr(result));
    }

    @Test
    public void aiGeneratorFacadeTest() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("任务记录网站", CodeGenTypeEnum.MULTI_FILE);
        Assertions.assertNotNull(file);
    }

}
