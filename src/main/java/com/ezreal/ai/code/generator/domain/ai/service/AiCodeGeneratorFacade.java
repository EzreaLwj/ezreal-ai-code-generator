package com.ezreal.ai.code.generator.domain.ai.service;

import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;
import com.ezreal.ai.code.generator.domain.ai.service.saver.FileCodeSaver;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            case HTML -> FileCodeSaver.saveHtmlCodeResult(aiCodeGeneratorService.generateHtmlCode(userMessage));
            case MULTI_FILE ->
                    FileCodeSaver.saveMultiFileCodeResult(aiCodeGeneratorService.generateMultiFileCode(userMessage));

            default -> {
                String message = "不支持该生成类型：" + codeGenTypeEnum.getValue();
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), message);
            }
        };
    }
}
