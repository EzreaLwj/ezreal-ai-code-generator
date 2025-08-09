package com.ezreal.ai.code.generator.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 6249033814872332541L;

    private String code;
    private String info;
    private T data;

}
