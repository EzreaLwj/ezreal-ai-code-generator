package com.ezreal.ai.code.generator.model.request;

import lombok.Data;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
@Data
public class UserRegisterRequest {

    private String userAccount;

    private String password;

    private String checkPassword;
}
