package com.ezreal.ai.code.generator.domain.ai.service.factory;

import com.ezreal.ai.code.generator.domain.ai.service.AiCodeGeneratorService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Component
public class AiCodeGeneratorServiceFactory {

    @Resource
    private ChatModel chatModel;

    @Bean
    public AiCodeGeneratorService create() {
        return AiServices.create(AiCodeGeneratorService.class, chatModel);
    }
}
