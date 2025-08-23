package com.ezreal.ai.code.generator.domain.ai.service;

import com.ezreal.ai.code.generator.domain.ai.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.MultiFileCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;
import com.ezreal.ai.code.generator.domain.ai.service.parser.CodeParserExecutor;
import com.ezreal.ai.code.generator.domain.ai.service.saver.FileCodeSaverExecutor;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * ai调用门面
 *
 * @author Ezreal
 * @Date 2025/8/9
 */
@Slf4j
@Service
public class AiCodeGeneratorFacade {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {

        if (codeGenTypeEnum == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "生成类型为空");
        }

        return switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode(userMessage);
                yield FileCodeSaverExecutor.execute(htmlCodeResult, codeGenTypeEnum);
            }
            case MULTI_FILE -> {
                MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                yield FileCodeSaverExecutor.execute(multiFileCodeResult, codeGenTypeEnum);
            }
            default -> {
                String message = "不支持该生成类型：" + codeGenTypeEnum.getValue();
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), message);
            }
        };
    }

    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {

        if (codeGenTypeEnum == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "生成类型为空");
        }

        return switch (codeGenTypeEnum) {
            case HTML -> {
                Flux<String> flux = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
                yield generateCodeStream(flux, codeGenTypeEnum);
            }
            case MULTI_FILE -> {
                Flux<String> flux = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield generateCodeStream(flux, codeGenTypeEnum);
            }
            default -> {
                String message = "不支持该生成类型：" + codeGenTypeEnum.getValue();
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), message);
            }
        };
    }

    private Flux<String> generateCodeStream(Flux<String> flux, CodeGenTypeEnum codeGenTypeEnum) {
        StringBuilder codeBuilder = new StringBuilder();
        return flux.doOnNext(codeBuilder::append)
                .doOnComplete(() -> {
                    try {
                        String result = codeBuilder.toString();
                        Object parsedResult = CodeParserExecutor.execute(result, codeGenTypeEnum);
                        File file = FileCodeSaverExecutor.execute(parsedResult, codeGenTypeEnum);
                        log.info("文件保存成功，路径为：{}", file.getAbsolutePath());
                    } catch (Exception e) {
                        log.error("文件保存异常", e);
                    }
                });
    }
}
