package com.ezreal.ai.code.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteEnum {

    NO(0),
    YES(1);;
    private final int code;
}
