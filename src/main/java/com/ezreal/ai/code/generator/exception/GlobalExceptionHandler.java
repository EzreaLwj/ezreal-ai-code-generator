package com.ezreal.ai.code.generator.exception;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public Response<?> businessExceptionHandler(AppException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ResponseCode.ILLEGAL_PARAMETER, "系统错误");
    }
}
