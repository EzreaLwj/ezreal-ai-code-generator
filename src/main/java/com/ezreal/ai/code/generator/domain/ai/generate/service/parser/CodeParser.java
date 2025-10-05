package com.ezreal.ai.code.generator.domain.ai.generate.service.parser;

/**
 * @author Ezreal
 * @Date 2025/8/23
 */
public interface CodeParser<T> {

    T parse(String code);
}
